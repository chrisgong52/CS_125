# CS_125

## Introduction
This project is designed to help store users' data and create recommendations
for them to improve their health. It starts by storing different aspects of a
person's schedule like sleep (hours), exercise (hours).

## TO RUN
Set Android Studio up and import this project into Android Studio. Ensure the Python 3.11 is installed, along with Java 1.8. Make sure to sync your project with the gradle, especially on first time use. This may take awhile to install all the proper packages. Once the project is set up, use an Android emulator that is compatible with Android 8.0.0 to run the app. If it is your first time, you will be taken to the Register page for a one time use to register your name. 

## THE APP
The Calendar will show what was tracked on a certain day. Of course, this can only be seen after multiple days of use.
The Recommendation page shows recommendations for how much sleep and exercise you should get. There is also a button to track your sleep and exercise for the day at the top.
The Settings page has an option to change your target weight or current weight.


## EXPERIMENTING WITH DATABASE
To do this, run app/src/main/python/main.py

"quit" - quit the program

"find {user_id}" - find user and all info associated with user_id

"create {name}" - create a user with a given name

"update {user_id {}" - updates info for given user_id
