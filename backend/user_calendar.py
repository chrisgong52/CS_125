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
            keeps track of previous week's sleep
        '''
        self.prev_sleep = {
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
            keeps track of previous week's exercise
        '''
        self.prev_exercise = {
            "Sun": -1,
            "Mon": -1,
            "Tues": -1,
            "Wed": -1,
            "Thurs": -1,
            "Fri": -1,
            "Sat": -1
        }
        self.function_table = {
            "meals": self.add_meal,
            "sleep": self.add_sleep,
            "exercise": self.add_exercise,
            "new_sleep": self.new_sleep,
            "new_exercise": self.new_exercise,
        }
        self.days = set([
            "Sun",
            "Mon",
            "Tues",
            "Wed",
            "Thurs",
            "Fri",
            "Sat"
        ])
        # self.__dict__["function_table"] = ""
        # self.__dict__["days"] = ""

    def convert_dict(self):
        temp = self.__dict__
        temp.pop("function_table")
        temp.pop("days")
        return temp

    '''
        prompt user to override the previous entry; "Y" for yes, "N" for no
        return true to override, else false
    '''
    def override_entry(self):
        temp = input("would you like to override the previous entry? (Y/ N): ")
        # print only for testing
        print(temp)
        return temp.strip(" \n") == "Y"

    '''
        add_meal adds a meal to the given day specified in the parameter
        checks to see if day is valid and adds the meal

        return True if day valid and meal added, else false
    '''
    def add_meal(self, day: str, meal: str) -> bool:
        if day in self.meals.keys():
            if self.meals[day] == "" or self.override_entry():
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
            if self.sleep[day] == -1 or self.override_entry():
                self.sleep[day] = sleep
                return True
            else:
                return False
        else:
            return False
        
    '''
        add_exercise adds the amount of exercise a user got in a day
        and checks to see if the day is valid

        return True if day valid and hours added, else false
    '''
    def add_exercise(self, day: str, exercise: int) -> bool:
        if day in self.exercise.keys():
            if self.exercise[day] == -1 or self.override_entry():
                self.exercise[day] = exercise
                return True
            else:
                return False
        else:
            return False
        
    '''
        modify the corresponding table with an update
        table: specify the dictionary that we'll be modifying
            "meals"
            "sleep"
            "exercise"
        day is the day to modify
        update is the update to change the table
    '''
    def update_attribute(self, table, day: str, update) -> bool:
        if day in self.days and table in self.function_table.keys():
            self.function_table[table](day, update)

    '''
        change the current sleep to prev_sleep and create a new sleep dict
    '''
    def new_sleep(self):
        self.prev_sleep = self.sleep
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
        change the current exercise to prev_exercise and create a new exercise dict
    '''
    def new_exercise(self):
        self.prev_exercise= self.exercise
        self.exercise = {
            "Sun": -1,
            "Mon": -1,
            "Tues": -1,
            "Wed": -1,
            "Thurs": -1,
            "Fri": -1,
            "Sat": -1
        }