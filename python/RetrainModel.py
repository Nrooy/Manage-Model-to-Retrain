
import Format_Data ,Def_Train_Model
import pandas as pd


def Retrain(x,y) :
    link = Def_Train_Model.save_model(x,y)
    Score =Def_Train_Model.Score(x,y)
    result = {
        "name": link[0],
        "link": link[1],
        "acc" : Score[0],
        "pre" : Score[1],
        "re" : Score[3],
        "f1" : Score[2],
        "dafault":1
    }
    return result
# x = pd.read_csv('./Data/DemoSampleProcess2111.csv')
# y =pd.read_csv('./Data/LabelDemo.csv')
# print(Retrain(x,y))