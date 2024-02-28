import pandas as pd
import  numpy as np
import csv
import json
import ast
from fastapi.responses import JSONResponse

def process_Sample1():
    label_df = pd.read_csv('./Data/LabelDemo2111.csv')
    data = pd.read_csv('./Data/Demo2111.csv')
    # Chuyển trường "tags" từ chuỗi thành danh sách (list)
    data['tags'] = data['tags'].apply(lambda x: x.split('|'))
    # Lấy tất cả các danh mục khác nhau
    unique_categories = label_df['tags']
    # Tạo cột cho mỗi danh mục và điền mã nhị phân
    for category in unique_categories:
        data[category] = data['tags'].apply(lambda x: 1 if category in x else 0)

    # Lưu DataFrame mới vào file CSV
    data.to_csv('./Data/OutputDemo2111.csv', index=False)
def Save_Sample_to_csv(Json_Data):
    samples_data = [{'id': sample.id, 'tags': sample.tags, 'used_id': sample.used_id} for sample in
                    samples]
    # Tạo DataFrame từ dữ liệu
    df = pd.DataFrame(samples_data)
    # Lưu DataFrame vào file CSV
    df.to_csv('./Data/Demo2111.csv', columns=['id_user', 'tags'], index=False)
    name ='./Data/Demo2111.csv'
    df.to_csv(name, columns=['id_user', 'tags'], index=False)
    return name
def Save_Label_to_csv():
    df = pd.read_csv('./Data/Demo2111.csv')
    # Chọn cột có dạng giá trị được ngăn cách bởi dấu "|"
    tags_column = df['tags']
    # Tách các giá trị trong cột tags
    tags_split = tags_column.str.split('|')
    # Tạo một danh sách chứa tất cả các giá trị khác nhau từ cột tags
    all_tags = set()
    for tag_list in tags_split:
        all_tags.update(tag_list)

    # Tạo DataFrame mới từ danh sách các giá trị khác nhau
    result_df = pd.DataFrame(list(all_tags), columns=['tags'])
    # Lưu DataFrame mới vào file CSV
    result_df.to_csv('./Data/LabelDemo2111.csv', index=False)
# def GetUniqueCategories(Json_Data):
#     # Đọc dữ liệu từ file CSV
#     data = Convert_to_csv(pd.read_json(Json_Data))
#
#     # Chuyển trường "tags" từ chuỗi thành danh sách (list)
#     data['tags'] = data['tags'].apply(ast.literal_eval)
#
#     # Lấy tất cả các danh mục khác nhau và loại bỏ dấu nháy kép
#     unique_categories = set(category for tags in data['tags'] for category in tags)
#     unique_categories = [category.replace('"', '') for category in unique_categories]
#
#     return unique_categories

# def convert_to_Json():
#     # Đọc tệp CSV và chỉ lấy hai cột đầu tiên
#     df = pd.read_csv('./Data/mainData1.csv', usecols=[0, 1])
#
#     # Bỏ dấu ngoặc kép từ trường "tags"
#     df['tags'] = df['tags'].str.replace('"', '')
#
#     # Chuyển dữ liệu thành một danh sách các từ điển
#     data = df.to_dict(orient='records')
#
#     # Chuyển danh sách từ điển thành JSON
#     json_data = json.dumps(data)
#     return json_data

# def Convert_to_csv(Json_Data):
#     df = pd.DataFrame(Json_Data)
#
#     # Loại bỏ các giá trị NaN khỏi DataFrame
#     df_cleaned = df.dropna()
#
#     # Lưu DataFrame đã làm sạch vào tệp CSV
#     df_cleaned.to_csv('./Data/sample.csv', index=False)
#
#     # Đọc lại tệp CSV để kiểm tra và trả về JSON
#     new_csv = pd.read_csv('./Data/sample.csv')
#     # json_result = new_csv.to_json(orient="records")
#
#     return new_csv
# def Process_Sample():
#     # Đọc dữ liệu từ file CSV
#     # data = Convert_to_csv(pd.read_json(Json_Data))
#     data = pd.read_csv('./Data/sample.csv')
#     # Chuyển trường "tags" từ chuỗi thành danh sách (list)
#     data['category'] = data['category'].apply(ast.literal_eval)
#     # Lấy tất cả các danh mục khác nhau
#     unique_categories = set(category for tags in data['category'] for category in tags)
#
#     # Tạo cột cho mỗi danh mục và điền mã nhị phân
#     for category in unique_categories:
#         data[category] = data['category'].apply(lambda x: 1 if category in x else 0)
#
#     # Lưu kết quả vào tệp CSV
#     data.to_csv('./Data/SampleDemo.csv', index=False)
#     return data

# Đọc dữ liệu từ file CSV
# def Process_Sample1(file_input):
#     # Đọc dữ liệu từ file CSV
#     data = pd.read_csv(file_input)
#     # Chuyển trường "tags" từ chuỗi thành danh sách (list)
#     data['tags'] = data['tags'].apply(lambda x: x.split('|'))
#     # Lấy tất cả các danh mục khác nhau
#     unique_categories = set(category for categories in data['tags'] for category in categories)
#     # Tạo cột cho mỗi danh mục và điền mã nhị phân
#     for category in unique_categories:
#         data[category] = data['tags'].apply(lambda x: 1 if category in x else 0)
#     # Lưu kết quả vào tệp CSV
#     name = "./Data/DemoSampleProcess2111.csv"
#     data.to_csv('./Data/DemoSampleProcess2111.csv', index=False)
#     return name
# def Process_Label(Array_Data):
#     df = pd.DataFrame({"tags": Array_Data})
#     # Lưu DataFrame vào tệp CSV
#     df.to_csv("./Data/LabelDemo1.csv", index=False)
#     return df