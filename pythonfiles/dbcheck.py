import mysql.connector

number=308
mydb1 = mysql.connector.connect(
    host="localhost",

    user="root",
    passwd="",

    database="test1"
)
qeury= "UPDATE sms SET text = 'hi hi' WHERE number="+ str(number)
mycursor = mydb1.cursor()
mycursor.execute(qeury)
mydb1.commit()
for row in mycursor:
    print(row[0])