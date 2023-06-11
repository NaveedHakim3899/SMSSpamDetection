import mysql.connector
import _thread as t
from profanity_check import predict, predict_prob
import pandas as pd
from pylab import *
import tensorflow as tf
import keras
from keras import backend as K
import csv

MODEL_NAME = 'mnist_convnet'
threshold=0.05
#this file is to take contentbased features out of text and apply a neural network on that
def preprocess(filename):


    filename.rename(columns={'Category': 'Class', 'Message': 'Text'}, inplace=True)
    #remove blank rows
    filename['Text'].dropna(inplace=True)

    # Count the number of words in each Text
    filename['Count'] = 0
    filename['profanity_level'] = 0
    filename['MathematicalChar'] = 0
    filename['UrlCheck'] = 0
    filename['PhonenumPresent'] = 0
    filename['naiveresult']=0
    filename['numClass'] = filename['Class'].map({'ham': 0, 'spam': 1})

    return filename





def feature_extractor(prepocessedFile):
    for i in np.arange(0, len(prepocessedFile.Text)):
        prepocessedFile.loc[i, 'Count'] = len(prepocessedFile.loc[i, 'Text'])
        prepocessedFile.loc[i, 'profanity_level'] = predict_prob(['prepocessedFile.loc[i,Text])'])
        prepocessedFile.loc[i, 'MathematicalChar'] = mathematicalcheck(prepocessedFile.loc[i, 'Text'])
        prepocessedFile.loc[i, 'MathematicalChar'] = mathematicalcheck(prepocessedFile.loc[i, 'Text'])
        prepocessedFile.loc[i, 'UrlCheck'] = urlcheck(prepocessedFile.loc[i, 'Text'])
        prepocessedFile.loc[i, 'PhonenumPresent'] = phonenumP(prepocessedFile.loc[i, 'Text'])


    return prepocessedFile
def mathematicalcheck(textstring):
        # Make own character set and pass
        # this as argument in compile method
        regex = re.compile('[@!#$%^&()<>?/\|}{~:+]')

        # Pass the string in search
        # method of regex object.
        if (regex.search(textstring) == None):
            return 0

        else:
            return 1


def urlcheck(textstring):
    # Make own character set and pass
    # this as argument in compile method
    regex1 = re.compile('www\.|http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]  |[!*\(\), ]|(?:%[0-9a-fA-F][0-9a-fA-F]))+')

    # Pass the string in search
    # method of regex object.
    if (regex1.search(textstring) == None):
        return 0
    else:
        return 1

def phonenumP(text):
    numbers = sum(c.isdigit() for c in text)
    if (numbers>0):
        return 1
    else:
        return 0



data = pd.read_csv('newdataset.csv', encoding='latin-1')

data = preprocess(data)

data = feature_extractor(data)
data.groupby('Class').describe()
X = data.iloc[:, 3:9]
Y = data.iloc[:, 9:10]
print(data.head())
print(X.head())
print(Y.head())
# Unique values in target set
#print(data.head())

#print(Y.head())
model = keras.Sequential()
model.add(keras.layers.Dense(6, input_dim=6, activation='relu'))
model.add(keras.layers.Dense(10, activation='relu'))
model.add(keras.layers.Dense(10, activation='relu'))
model.add(keras.layers.Dense(1, activation='sigmoid'))

model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
model.fit(X, Y, epochs=60, batch_size=32)

mydb = mysql.connector.connect(
    host="localhost",

    user="root",
    passwd="",

    database="messagedatabase"
)
while(1):
    mydb = mysql.connector.connect(
        host="localhost",

        user="root",
        passwd="",

        database="messagedatabase"
    )
    mycursor = mydb.cursor()
    mycursor.execute(" SELECT COUNT(*) from smsdata")
    for row in mycursor:
        N=(row[0])
        #print('no of rows in db', N)


    # # to read froim file
    f = open('noOfRows.txt', "r")
    if f.mode == 'r':
        b= f.read()
        f.close()
       # print('No of rows in file',b)

    diffrence= N-int(b)
    #print('diffrence is' , diffrence)
  #  diffrence=1;
    if(diffrence>0):
        mycursor = mydb.cursor()
        mycursor.execute(
            " SELECT * FROM (  SELECT * FROM smsdata ORDER BY id DESC LIMIT " + str(diffrence) + ") sub ORDER BY id ASC ")
        for row in mycursor:
            number = row[0]
            text = row[2]
            isincontact=row[4]
            print(text)
            print(number)
            with open('data.csv', 'a') as employee_file:
                employee_writer = csv.writer(employee_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
                employee_writer.writerow([0, text,isincontact])

        data1 = pd.read_csv('data.csv', encoding='latin-1')

        data1 = preprocess(data1)

        data1 = feature_extractor(data1)
        data.groupby('Class').describe()
        mydb1 = mysql.connector.connect(
                host="localhost",

                user="root",
                passwd="",

                database="messagedatabase"
            )
        mycursor1 = mydb1.cursor()
        mycursor1.execute(" SELECT COUNT(*) from smsdata")
        for row in mycursor1:
         N = (row[0])
        # file name create if file was not there
         fill = open('noOfRows.txt', 'w+')
         fill.write(str(N))
         fill.close()
         f = open('noOfRows.txt', "r")
         if f.mode == 'r':
             b = f.read()

        xtest = data1.iloc[:, 3:9]
       # print(xtest)
        ytest = data1.iloc[:, 9:10]
        test = model.predict(xtest)
        #print (len(test))
        length= len(test)-1
        test1 = test[length]
        test2=test1;
        print(test2)
        if (test2 > threshold ):
            mydb1 = mysql.connector.connect(
                host="localhost",

                user="root",
                passwd="",

                database="messagedatabase"
            )
            print('i am spam')
            mycursor2= mydb1.cursor();
            qeury= "UPDATE smsdata SET Spam = 1 WHERE id="+str(number)
            mycursor2.execute(qeury)
            mydb1.commit()
            print(number)
            print(str(number))
        else:
            mydb1 = mysql.connector.connect(
                host="localhost",

                user="root",
                passwd="",

                database="messagedatabase"
            )
            mycursor2 = mydb1.cursor();
            qeury = "UPDATE smsdata SET Spam = 0 WHERE id=" + str(number)
            mycursor2.execute(qeury)
            mydb1.commit()
            print(number)
            print(str(number))
   # else:
       # print("no change in db")

    # to write in file
    mycursor1 = mydb.cursor()
    mycursor1.execute(" SELECT COUNT(*) from smsdata")
    for row in mycursor1:
        N = (row[0])
    # file name create if file was not there
    fill = open('noOfRows.txt', 'w+')
    fill.write(str(N))
    fill.close()
    mydb.close


