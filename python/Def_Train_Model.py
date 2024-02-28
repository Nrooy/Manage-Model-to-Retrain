import ChuanHoa
import pandas as pd
import numpy as np
import skmultilearn
from sklearn.naive_bayes import GaussianNB,MultinomialNB
from sklearn.metrics import accuracy_score, hamming_loss, precision_score, recall_score
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
import neattext as nt
import neattext.functions as nfx
from sklearn.metrics import f1_score, accuracy_score
from skmultilearn.problem_transform import BinaryRelevance
from skmultilearn.problem_transform import ClassifierChain
from skmultilearn.problem_transform import LabelPowerset
import pickle
import os
from ChuanHoa import *

def split_data(x,y):
    x['used_id'].apply(lambda x: nt.TextFrame(x).noise_scan())
    x['used_id'].apply(lambda x: nt.TextExtractor(x).extract_stopwords())
    x['used_id'].apply(nfx.remove_stopwords)
    corpus = x['used_id'].apply(nfx.remove_stopwords)
    tfidf = TfidfVectorizer()
    Xfeatures = tfidf.fit_transform(corpus).toarray()
    m = x[y['tags']]
    X_train, X_test, y_train, y_test = train_test_split(Xfeatures, m, test_size=0.3, random_state=42)
    return X_train , X_test , y_train ,y_test

def create_model(x,y):
    X_train = split_data(x,y)[0]
    y_train =split_data(x,y)[2]
    binary_rel_clf = BinaryRelevance(MultinomialNB())
    binary_rel_clf.fit(X_train, y_train)
    return  binary_rel_clf
def save_model(x,y):
    binary_rel_clf = create_model(x,y)
    file_name = "model" + ChuanHoa.time_to_string()
    absolute_path = os.path.join("D:/TAI_LIEU/Ky_1_nam_4/Phat_Trien_He_Thong_Thong_Thong_minh/venv/Model/", file_name)
    with open (absolute_path,'wb') as file:
        pickle.dump(binary_rel_clf,file)
    return file_name , absolute_path
def predic_Model(x,y):
    X_test = split_data(x,y)[1]
    binary_rel_clf = create_model(x,y)
    BinaryRelevance(classifier=MultinomialNB(alpha=1.0, class_prior=None,
                                             fit_prior=True),
                    require_dense=[True, True])
    br_prediction = binary_rel_clf.predict(X_test)
    br_prediction.toarray()
    return br_prediction

def Score(x,y):
    y_test = split_data(x,y)[3]
    br_prediction = predic_Model(x,y)
    acc = round(round(accuracy_score(y_test,br_prediction),4)*100,2)
    pre = round(round(precision_score(y_test, br_prediction, average='macro', zero_division=0), 4)*100,2)
    f1 = round(round( f1_score(y_test,br_prediction,average='macro',zero_division=0) , 4 )*100,2)
    re = round(round( recall_score(y_test,br_prediction,average='macro',zero_division=0 ),4)*100,2)
    return acc,pre,f1,re


def Render_result(Link_Model_isActive,input, Nhan , Sample):
    file_name = Link_Model_isActive
    #read_data
    df = pd.read_csv(Sample)
    nhan = pd.read_csv(Nhan)

    with open(file_name, 'rb') as file:
        new_model = pickle.load(file)
    y = df[nhan['tags']].iloc[0]
    #process data
    corpus = df['used_id'].apply(nfx.remove_stopwords)
    tfidf = TfidfVectorizer()
    Xfeatures = tfidf.fit_transform(corpus).toarray()

    new_sample_array = tfidf.transform([input]).toarray()
    new_prediction = new_model.predict(new_sample_array)
    new_prediction = new_prediction.toarray()

    predicted_labels = []
    for j, label in enumerate(y.index):
        if 1 in new_prediction[:, j]:
            predicted_labels.append(label)
    return predicted_labels

# Link_Model_isActive = 'D:/TAI_LIEU/Ky_1_nam_4/Phat_Trien_He_Thong_Thong_Thong_minh/venv/model20231030222734'
# input = "AGYHHIERNXKA6P5T7CZLXKVPT7IQ"
# Nhan = "LabelDemo.csv"
# Sample="SampleDemo.csv"
# print(Render_result(Link_Model_isActive,input,Nhan,Sample))
# df = pd.read_csv('./Data/DemoSampleProcess2111.csv')
# y =pd.read_csv('./Data/LabelDemo.csv')
# save_model(x,y)
# x = df[y['tags']]
# print(x)
# Link_Model_isActive = "D:/TAI_LIEU/Ky_1_nam_4/Phat_Trien_He_Thong_Thong_Thong_minh/venv/Model/model20231214043106"
# input = "AGYHHIERNXKA6P5T7CZLXKVPT7IQ"
# # Sample = "./Data/DemoSampleProcess2111.csv"
# # Nhan="./Data/LabelDemo.csv"
# Sample = "./Data/OutputDemo2111.csv"
# Nhan = "./Data/LabelDemo2111.csv"
# x= Render_result(Link_Model_isActive,input,Nhan,Sample)
# print(x)