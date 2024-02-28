import os
import Def_Train_Model
import pandas as pd
from Def_Train_Model import *
from RetrainModel import *
from fastapi import FastAPI
from enum import Enum
from pydantic import BaseModel
app =FastAPI()
import httpx
from fastapi import FastAPI
from Format_Data import *
from fastapi import HTTPException
app = FastAPI()

from typing import List
class Samples(BaseModel):
    id: int
    tags : str
    used_id: str
class User(BaseModel):
    id: int
    age: int
    category : str
    code: str
    email: str
    gender : str
    password:str
    role:int
    userName: str
class Favor(BaseModel):
    name : str
@app.post("/result1")
async def get_data_from_other_api1(user: User):
    api_url = "http://localhost:8080/send_name_model_active"
    async with httpx.AsyncClient() as client:
        response = await client.get(api_url)
        if response.status_code == 200:
            data = response.json()["name"]
        else:
            return {"error": "Không thể lấy dữ liệu "}
    Link_Model_isActive = "D:/TAI_LIEU/Ky_1_nam_4/Phat_Trien_He_Thong_Thong_Thong_minh/venv/Model/model20231214043106"
    print(Link_Model_isActive)
    input = user.code
    Sample = "./Data/OutputDemo2111.csv"
    Nhan = "./Data/LabelDemo2111.csv"
    return Render_result(Link_Model_isActive, input, Nhan, Sample)


@app.post("/receive_sample")
def receive_sample(samples: List[Samples]):
    try:
        samples_data = [{'id': sample.id, 'tags': sample.tags, 'used_id': sample.used_id} for sample in
                        samples]
        # Tạo DataFrame từ dữ liệu
        df = pd.DataFrame(samples_data)
        # Lưu DataFrame vào file CSV
        df.to_csv('./Data/Demo2111.csv', columns=['used_id', 'tags'], index=False)
        #Chuyển sang dạng 0,1 để demo:
        Save_Label_to_csv()
        # Process_Sample1('./Data/Demo2111.csv')
        process_Sample1()
        x = pd.read_csv('./Data/OutputDemo2111.csv')
        y =pd.read_csv('./Data/LabelDemo2111.csv')
        result = Retrain(x,y);
        return result
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/delete_model/")
async def delete_model(linkModel: dict):
    link = "D:/TAI_LIEU/Ky_1_nam_4/Phat_Trien_He_Thong_Thong_Thong_minh/venv/Model/"+linkModel.get("linkModel")
    os.remove(link)

@app.get("/result")
async def get_data_from_other_api():
    # api_url = "http://127.0.0.1:5000/get_name_model"
    api_url= "http://localhost:8080/send_name_model_active"
    async with httpx.AsyncClient() as client:
        response = await client.get(api_url)
        if response.status_code == 200:
            data = response.json()["name"]
        else:
            return {"error": "Không thể lấy dữ liệu "}
    Link_Model_isActive = "D:/TAI_LIEU/Ky_1_nam_4/Phat_Trien_He_Thong_Thong_Thong_minh/venv/Model/"+data
    input = "AGYHHIERNXKA6P5T7CZLXKVPT7IQ"
    Sample = "./Data/OutputDemo2111.csv"
    Nhan="./Data/LabelDemo2111.csv"
    return Render_result(Link_Model_isActive,input,Nhan,Sample)

# @app.get("/get_Labels")
# async def get_Label():
#     api_url ="http://localhost:8080/get_all_Label"
#     async with httpx.AsyncClient() as client:
#         response = await client.get(api_url)
#         if response.status_code == 200:
#             data = response.json()
#             category_list = [item["name"] for item in data]
#             Process_Label(category_list)
#             return category_list
#         else:
#             return {"error": "Không thể lấy dữ liệu "}
# @app.get("/get_Samples")
# async def get_Label():
#     api_url ="http://127.0.0.1:8080/api/model"
#     async with httpx.AsyncClient() as client:
#         response = await client.get(api_url)
#         if response.status_code == 200:
#             data = response.json()
#             Convert_to_csv(data)
#             return Process_Sample1()
#         else:
#             return {"error": "Không thể lấy dữ liệu "}
# @app.get("/process_data/")
# async def process_data():
#     x= pd.read_csv('./Data/mainData1.csv')
#     y= pd.read_csv('./Data/LabelDemo.csv')
#     result = Retrain(x,y);
#     return result
# @app.get("/get_name_model")
# async def get_data_from_other_api():
#     api_url = "http://localhost:8080/send_name_model_active/"
#     async with httpx.AsyncClient() as client:
#         response = await client.get(api_url)
#         if response.status_code == 200:
#             data = response.json()
#             return data['name']
#         else:
#             return {"error": "Không thể lấy dữ liệu "}
