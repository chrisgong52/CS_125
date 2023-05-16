import user
import random
import database


db_string = "mongodb+srv://19christopherg:ChrisMongo52!@cs125.fufk0kh.mongodb.net/"
'''
    k_users is a dictionary mapping the ids of users to the corresponding
    person object
'''
k_users = {}

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
    dictionary

    return the id of the person created to send to the frontend
'''
def create(commands: list):
    print('create', commands)
    id = random.randint(0, 100000)
    while id in k_users.keys():
        id = random.randint(0, 100000)
    k_users[id] = user.Person(commands[0])
    print("created", commands[0])
    return id
    

'''
    k_func_table is the function pointer table that maps commands to the
    corresponding functions
'''
k_func_table = {
    "quit": "",
    "create": create,
}

if __name__ == "__main__":
    '''
        list of commands:
            create (person name)
            sleep (person id)
    '''

    db = database.Database()
    new_user = user.Person("Peining Zhang")
    db.update_user("Peining Zhang", "meals", "Thurs", "Pho")
    # db.delete("Andy Gong")
    # db.insert(new_user)

    # res = db.find("Andy Gong")
    # print(res)
    # while True:
    #     command_input = input("enter a command: ")
    #     command = parse_input(command_input)
    #     if command != -1:
    #         if command[0] == "quit":
    #             break
    #         k_func_table[command[0]](command[1:])
    #     else:
    #         continue