
import database
import user
import user_calendar

#THESE 3 LINES NEEDED FOR ANDROID APP TO CONNECT
import dns.resolver
dns.resolver.default_resolver=dns.resolver.Resolver(configure=False)
dns.resolver.default_resolver.nameservers=['8.8.8.8']

# user goal will be determined by a function of regular healthy exercise levels + additional exercise for health goals
# personal additions are intended as additional context: if the person gets more/less exercise than recommended, slightly
# increase/decrease personal additions (unless already at 0)
# weight loss goals will be calculated approximately
# 150 minutes base + personal changes + weight loss goals

# will be personalized based on health goal
# will use context of last week

# if day where this week exercise < prev week exercise, recommend increasing that day
# if day where this week exercise > prev week exercise, recommend maintaining that level
# if day where exercise < 30 min, recommend adding exercise
# if day where this week exercise = prev week exercise, dont use in recommendation (unless < 1)
# if stable but not hitting a health goal, recommend an increase across all days

# main func: do we get to our goal? again, classification is easy but the recommendation is more important
# return yes or no and a list of all recommendations detected, could probably do it as a list of strings

# how i expect this to get to the front end:
# at the end of the week (ie some day at 12am), call a weekly update function that changes this week's info to
# prev week's info and also calls both recommender functions. im not sure how, but send this info to the front end
# (probably ask jadon) and have the strings be input in a recommendations area (either in the sleep page or a main page)

BASE = 150

def mtoh_string(minutes):
    hours = minutes // 60
    minutes = minutes - (hours * 60)
    if hours == 0:
        return str(minutes) + " minutes"
    else:
        if hours == 1:
            return str(hours) + " hour and " + str(minutes) + " minutes"
        else:
            return str(hours) + " hours and " + str(minutes) + " minutes"

def calculate_goal(current_user):
    # for every pound over goal, add 30 sec of exercise
    # in reality, diet is actually more important for weight loss :/
    stats = current_user['stats']
    goals = current_user['goals']
    diff = 0
    if stats['weight'] > goals['weight']:
        diff = stats['weight'] - goals['weight']
    return diff

def exercise_type(user):
    strength_training = False
    cardio = False

    stats = user['stats']
    goals = user['goals']
    if stats['weight'] < goals['weight']:
        cardio = True
    if stats['weight'] > goals['weight']:
        strength_training = True
    if stats['fat_percent'] > goals['fat_percent']:
        strength_training = True
        cardio = True

    if cardio and strength_training:
        return "cardio and strength training"
    elif cardio:
        return "cardio"
    elif strength_training:
        return "strength training"
    else:
        return "cardio"

def exercise_recommend(user_id):
    db = database.Database()
    current_user = db.find(user_id)
    current_calendar = current_user['calendar']
    this_week = current_calendar['exercise']
    prev_week = current_calendar['prev_exercise']
    days = this_week.keys()

    total = 0
    recommendations = []

    calculate_goal(current_user)

    for day in days:

        current_min = this_week[day]
        prev_min = prev_week[day]
        if current_min != -1:
            total += current_min
            if current_min < prev_min:
                recommendations.append("Last week, you exercised " + mtoh_string(prev_min - current_min) + " more than this week on " + day + ", do your best to get back to that level next week!")
            elif current_min > prev_min:
                recommendations.append("You exercised " + mtoh_string(current_min - prev_min) + " more on " + day + " this week than last week, try your best to maintain that level next week!")
            if current_min < 30:
                recommendations.append("Try to exercise for at least 30 minutes on " + day + "!")
    total_goal = calculate_goal(current_user) + BASE
    recommendations.append("According to your health goals, we recommend focusing on " + exercise_type(current_user) + " the most.")
    if total < total_goal:
        recommendations.append("You haven't achieved your exercise goal yet, keep your recommendations in mind and keep going! You got this! :D")
    else:
        recommendations.append("You achieved your exercise goal! Keep up the good work!")

    return recommendations

def sleep_recommend(user_id):
    db = database.Database()
    current_user = db.find(user_id)
    current_calendar = current_user['calendar']
    this_week = current_calendar['sleep']
    prev_week = current_calendar['prev_sleep']
    days = this_week.keys()

    total = 0
    recommendations = []

    for day in days:
        current_min = this_week[day]
        prev_min = prev_week[day]
        if current_min != -1:
            total += current_min
            if current_min > 600:
                recommendations.append("Sleep is good for you, but too much is bad! Try to wake up within 10 hours on " + day + ".")
            elif current_min < prev_min:
                recommendations.append("Last week, you slept " + mtoh_string(prev_min - current_min) + " more than this week on " + day + ", do your best to get back to that level next week!")
            elif current_min > prev_min:
                recommendations.append("You slept " + mtoh_string(current_min - prev_min) + " more on " + day + " this week than last week, try your best to maintain that level next week!")
            if current_min < 420:
                recommendations.append("Try to sleep for at least 7 hours on " + day + "!")
    if total < BASE:
        recommendations.append("You haven't achieved your sleep goal yet, keep your recommendations in mind and try your best to make time to sleep!")
    else:
        recommendations.append("You achieved your sleep goal! Good job!")
    return recommendations


def get_all_recommendations(user_id):
    sleep_recs =sleep_recommend(user_id)
    exercise_recs = exercise_recommend(user_id)

    sleep_string = ''
    exercise_string = ''
    for rec in sleep_recs:
        sleep_string += rec + '\n'
    for rec in exercise_recs:
        exercise_string += rec + '\n'

    return {'sleep': sleep_string, 'exercise': exercise_string}


if __name__ == "__main__":
    x = get_all_recommendations(93221)
    print( x['exercise'])
    print( x['sleep'])