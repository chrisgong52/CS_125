import user_calendar

cur_calendar = user_calendar.Calendar()

command_mapping = {
    "add meal": "meals",
    "add sleep": "sleep",
    "add exercise": "exercise"
}

test_path = "/Users/chrisgong/Desktop/College/Spring_23/CS_125/test.txt"
open_file = open(test_path)
for item in open_file.readlines():
    print(item[:-1])
    item = item.split()
    if len(item) > 0:
        command = item[0] + " " + item[1]
        day = item[2]
        modification = ""
        for el in item[3:]:
            modification = modification + el + " "
        modification = modification[:-1]
        if command_mapping[command] == "exercise" or command_mapping[command] == "sleep":
            modification = int(modification)
        cur_calendar.update_attribute(command_mapping[command], day, modification)
        if command_mapping[command] == "meals":
            print("meals: ", cur_calendar.meals)
        if command_mapping[command] == "sleep":
            print("sleep: ", cur_calendar.sleep)
        if command_mapping[command] == "exercise":
            print("exercise: ", cur_calendar.exercise)