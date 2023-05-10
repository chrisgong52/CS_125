import user_calendar

class Person:
    def __init__(self, name):
        self.name = name
        self.calendar = user_calendar.Calendar()
        self.recs = {}
        self.stats = {}
        self.__dict__["calendar"] = self.calendar.__dict__

    '''
        update_calendar will update the object's calendar
        calendar attribute would be either: meal, sleep, or exercise
        day is the day to modify
        modification will be an array to update the calendar with
            using an array to allow for more general modifications in the future
    '''
    def __iter__(self):
        for item in self.__dict__:
            print(item)
            if item == self.calendar:
                yield self.calendar.__dict__
            else:
                yield item
        # return {self.name: self.name,
        #         self.calendar: self.calendar.__dict__,
        #         self.recs: self.recs,
        #         self.stats: self.stats,
        #     }
    def update_calendar(self, calendar_attribute, day, modification):
        self.calendar.update_attribute(calendar_attribute, day, modification)