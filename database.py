import pymongo
import user



class Database:
    def __init__(self, server = "mongodb+srv://19christopherg:ChrisMongo52!@cs125.fufk0kh.mongodb.net/", database = "User_DB"):
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
    def find(self, name):
        return self.records.find_one({"name": name})
    
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
    def update_user(self, name, table, day, change):
        temp = self.find(name)
        if table in temp["calendar"].keys() and day in temp["calendar"][table].keys():
            temp["calendar"][table][day] = change
            self.records.update_one({"name": name}, {"$set": temp})
            return True
        else:
            return False