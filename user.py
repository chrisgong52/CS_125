import user_calendar

class Person:
    def __init__(self, name):
        self.name = name
        self.calendar = user_calendar.Calendar()
        self.recs = {}
        self.stats = {}