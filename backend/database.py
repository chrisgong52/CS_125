import pymongo
import user



class Database:
    def __init__(self, server = "mongodb+srv://19christopherg:ChrisMongo52!@cs125.fufk0kh.mongodb.net/?tls=true&tlsAllowInvalidCertificates=true", database = "User_DB"):
        self.server = pymongo.MongoClient(server)
        self.db = self.server.get_database(database)
        self.records = self.db.User_Records

    '''
        insert a new user into the database
        add a bool return to make sure that user was added
            true if added, else false
    '''
    def insert(self, user):
        self.records.insert_one(user.convert_dict())
        
    '''
        return the user that was found
            change to use id instead of name
    '''
    def find(self, id):
        return self.records.find_one({"_id": id})
    
    '''
        find all users and return the found users in pymongo cursor object
        to be iterated through
    '''
    def find_all(self):
        for item in self.records.find({}):
            print(item)
        return self.records.find({})
    
    '''
        return the user that was deleted
            change to use id instead of name
    '''
    def delete(self, name):
        return self.records.delete_one({"name": name})
    
    '''
        update the user that was inputted
        parameters: name - key/ name of the user that will be changed
                    table - "meals," "sleep," or "exercise"
                    day - in the day set
                    change - the modification to the table
        return bool if record was updated
            change to use id instead of name
    '''
    def update_user_calendar(self, id, table, day, change):
        temp = self.find(id)
        if temp != None and table in temp["calendar"].keys() and day in temp["calendar"][table].keys():
            temp["calendar"][table][day] = change
            self.records.update_one({"_id": id}, {"$set": temp})
            return True
        else:
            return False
        
    def update_user_stats(self, id, attribute, change):
        temp = self.find(id)
        if temp != None and attribute in temp["stats"]:
            temp["stats"][attribute] = int(change) if attribute == "weight" or attribute == "fat_percent" else change
            self.records.update_one({"_id": id}, {"$set": temp})
            return True
        else:
            return False
        
    def update_user_goals(self, id, attribute, change):
        temp = self.find(id)
        if temp != None and attribute in temp["goals"]:
            temp["goals"][attribute] = int(change) if attribute == "weight" or attribute == "fat_percent" else change
            self.records.update_one({"_id": id}, {"$set": temp})
            return True
        else:
            return False
        
    def delete_all(self):
        return self.records.delete_many({})