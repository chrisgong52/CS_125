class Calendar:
    def __init__(self):
        '''
            meals will track the type of meals being consumed
            can be customized to keep track of macros
        '''
        self.meals = {
            "Sun": "",
            "Mon": "",
            "Tues": "",
            "Wed": "",
            "Thurs": "",
            "Fri": "",
            "Sat": ""
        }
        '''
            sleep keeps track of how much sleep a user has gotten
            on each day of a week
        '''
        self.sleep = {
            "Sun": -1,
            "Mon": -1,
            "Tues": -1,
            "Wed": -1,
            "Thurs": -1,
            "Fri": -1,
            "Sat": -1
        }
        '''
            exercise keeps track of how many hours of exercise a user
            has gotten over a week; it can be modified to track cardio
            vs weights, etc
        '''
        self.exercise = {
            "Sun": -1,
            "Mon": -1,
            "Tues": -1,
            "Wed": -1,
            "Thurs": -1,
            "Fri": -1,
            "Sat": -1
        }

    '''
        add_meal adds a meal to the given day specified in the parameter
        checks to see if day is valid and adds the meal

        return True if day valid and meal added, else false
    '''
    def add_meal(self, day: str, meal: str) -> bool:
        if day in self.meals.keys():
            self.meals[day] = meal
            return True
        else:
            return False
        
    '''
        add_sleep adds the amount of sleep a user got to a given day
        and checks to see if the day is valid

        return True if day valid and sleep added, else false
    '''
    def add_sleep(self, day: str, sleep: int) -> bool:
        if day in self.sleep.keys():
            self.sleep[day] = int
            return True
        else:
            return False
        
    '''
        add_exercise adds the amount of exercise a user got in a day
        and checks to see if the day is valid

        return True if day valid and hours added, else false
    '''
    def add_exercise(self, day: str, exercise: int) -> bool:
        if day in self.exercise.keys():
            self.exercise[day] = exercise
            return True
        else:
            return False