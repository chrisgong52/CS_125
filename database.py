import pymongo
import user

client = pymongo.MongoClient("mongodb+srv://19christopherg:hqtX4BhxGtR5S061@cs125.fufk0kh.mongodb.net/")

db = client.get_database("User_DB")

users = db.User_Records

new_user = user.Person("Peining")
print(new_user.__dict__)
# print(dict(new_user))
users.insert_one(new_user.__dict__)
# print(users.count_documents)
# print(users)