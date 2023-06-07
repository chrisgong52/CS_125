import user
import random
import database
import pymongo

db_string = "mongodb+srv://19christopherg:ChrisMongo52!@cs125.fufk0kh.mongodb.net/"

'''
    parse_input returns the command for a given input
    return -1 if invalid
'''
def parse_input(commands: str):
    temp = commands.strip(" \n").split()
    if len(temp) > 0 and temp[0] in k_func_table.keys():
        return temp
    return -1


'''
    create creates a new person object and adds that to the
    dictionary. commands will be a person's first and last name

    return the id of the person created to send to the frontend
'''
def create(commands: list, existing_ids: dict, db: database):
    id = random.randint(0, 100000)
    while id in existing_ids.keys():
        id = random.randint(0, 100000)
    existing_ids[id] = commands[0] + " " + commands[1]
    new_user = user.Person(commands[0] + " " + commands[1], id)
    db.insert(new_user)
    print("created", commands[0] + " " + commands[1])
    return id

'''
    given a user id, update the user's data
'''
def update(commands: list, existing_ids: dict, db: database):
    user_id = int(commands[0])
    command = commands[1]
    if command == "calendar":
        table = commands[2]
        day = commands[3]
        if table == "meals":
            change = ""
            for item in commands[4:]:
                change = change + item + " "
            change = change[:-1]
        else:
            change = int(commands[4])
        updated = db.update_user_calendar(user_id, table, day, change)
        print("updated calendar")
        return updated
    elif command == "stats" or command == "goals":
        attribute = commands[2]
        change = commands[3]
        if command == "stats":
            updated = db.update_user_stats(user_id, attribute, change)
            print("updated stats")
        else:
            updated = db.update_user_goals(user_id, attribute, change)
            print("updated goals")
        return updated
    elif command == "new_sleep":
        updated = db.update_user_sleep(user_id, "", "")
        print("updated sleep week")
    elif command == "new_exercise":
        updated = db.update_user_exercise(user_id, "", "")
        print("updated calendar week")
    else:
        pass
    return False


'''
    delete all users in current database
'''
def delete_all(commands: list, existing_ids: dict, db: database):
    print("deleted all")
    db.delete_all()
    

'''
    k_func_table is the function pointer table that maps commands to the
    corresponding functions
'''
k_func_table = {
    "quit": "",
    "create": create,
    "update": update,
    "delete_all": delete_all,
}

'''
    valid commands:
        quit
        create *name (first last)*
        update *user id*
            calendar *table*, *day*, *change*
            stats *attribute (height, weight, fat_percent)* *change*
            goals *attribute *(weight, fat_percent)* *change*
            new_sleep // doesn't take extra parameters, just cycles week
            new_exercise // doesn't take extra parameters, just cycles week
'''
def main():
    print(pymongo.version)
    db = database.Database()
    '''
        k_users is a dictionary mapping the ids of users to the corresponding
        person object
    '''
    k_users = {}
    print(k_users)
    while True:
        temp = input("enter a command: ")
        commands = temp.strip("\n").split()
        if commands[0] == "quit":
            return 0
        k_func_table[commands[0]](commands[1:], k_users, db)

if __name__ == "__main__":
    main()