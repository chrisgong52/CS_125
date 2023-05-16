import user_calendar

class Person:
    def __init__(self, name, id):
        self.name = name
        self.calendar = user_calendar.Calendar()
        self.recs = {}
        self.stats = {}
        self._id = id

    def __iter__(self):
        for item in self.__dict__:
            if item == self.calendar:
                yield self.calendar.__dict__
            else:
                yield item

    '''
        update_calendar will update the object's calendar
        calendar attribute would be either: meal, sleep, or exercise
        day is the day to modify
        modification will be an array to update the calendar with
            using an array to allow for more general modifications in the future
    '''
    def update_calendar(self, calendar_attribute, day, modification):
        self.calendar.update_attribute(calendar_attribute, day, modification)

    '''
        convert the person object to a dictionary that can be inserted into the database
    '''
    def convert_dict(self):
        temp = self.__dict__
        temp["calendar"] = self.calendar.convert_dict()
        # print(temp["calendar"])
        return temp
    
    '''
        update the stats dict (remember what this is for, forget atm)
    '''
    def update_stats(self):
        pass

    '''
        update the recs dict (figure out how to store the recommendations)
    '''
    def update_recs(self):
        pass
    
    def update_user(self, command, day, modification, calendar_attribute = ""):
        if command == "calendar":
            self.update_calendar(calendar_attribute, day, modification)
        elif command == "recs":
            self.update_recs()
        else:
            self.update_stats()