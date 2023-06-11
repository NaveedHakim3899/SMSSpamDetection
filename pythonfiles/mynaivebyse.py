
from profanity_check import predict, predict_prob
import pandas as pd
from pylab import *
from tensorflow import keras
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB

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
    filename['numClass'] = filename['Class'].map({'ham': 0, 'spam': 1})
    filename['naiveresult']= 0

    return filename


def feature_extractor(prepocessedFile):
    for i in np.arange(0, len(prepocessedFile.Text)):
        prepocessedFile.loc[i, 'Count'] = len(prepocessedFile.loc[i, 'Text'])
        prepocessedFile.loc[i, 'profanity_level'] = predict_prob(['prepocessedFile.loc[i,Text])'])
        #prepocessedFile.loc[i, 'profanity_level'] =0
        prepocessedFile.loc[i, 'MathematicalChar'] = mathematicalcheck(prepocessedFile.loc[i, 'Text'])
        prepocessedFile.loc[i, 'MathematicalChar'] = mathematicalcheck(prepocessedFile.loc[i, 'Text'])
        prepocessedFile.loc[i, 'UrlCheck'] = urlcheck(prepocessedFile.loc[i, 'Text'])
        prepocessedFile.loc[i, 'PhonenumPresent'] = phonenumP(prepocessedFile.loc[i, 'Text'])
        prepocessedFile

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



data = pd.read_csv('dataset.csv', encoding='latin-1')

data = preprocess(data)

#data = feature_extractor(data)
data.groupby('Class').describe()
X = data.iloc[:, 2:7]
Y = data.iloc[:, 7:8]
print(Y.shape)
print(X.shape)
print(X.head())
print(Y.head())
#Xinput = X.iloc[0:3000, :]
#Yinput = Y.iloc[:3000, :]
#xtest = X.iloc[3000:5572, :]
#ytest = Y.iloc[3000:5572, :]
vectorize = CountVectorizer()
counts = vectorize.fit_transform(data['Text'].values)
classifier = MultinomialNB()
targets = data['Class'].values
classifier.fit(counts, targets)
example = ['Free texting service ']
example_count = vectorize.transform(example)
predictions= classifier.predict(example_count)
print(predictions)
