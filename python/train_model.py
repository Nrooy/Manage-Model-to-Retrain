import pickle

import pandas as pd
import numpy as np
from sklearn.naive_bayes import GaussianNB,MultinomialNB
from sklearn.metrics import accuracy_score,hamming_loss
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
import neattext as nt
import neattext.functions as nfx
from sklearn.metrics import f1_score, accuracy_score ,precision_score,recall_score
from skmultilearn.problem_transform import BinaryRelevance
from skmultilearn.problem_transform import ClassifierChain
from skmultilearn.problem_transform import LabelPowerset

df = pd.read_csv("mainData1.csv")
#df['id_user'].apply(lambda x: nt.TextFrame(x).noise_scan())
#df['id_user'].apply(lambda x: nt.TextExtractor(x).extract_stopwords())
#df['id_user'].apply(nfx.remove_stopwords)
corpus = df['id_user'].apply(nfx.remove_stopwords)
tfidf = TfidfVectorizer()
Xfeatures = tfidf.fit_transform(corpus).toarray()
nhan = pd.read_csv("nhan1.csv")
y = df[nhan['Category']]
X_train, X_test, y_train, y_test = train_test_split(Xfeatures, y, test_size=0.3, random_state=42)

binary_rel_clf = BinaryRelevance(MultinomialNB())
binary_rel_clf.fit(X_train, y_train)
BinaryRelevance(classifier=MultinomialNB(alpha=1.0, class_prior=None,
                                             fit_prior=True),
                    require_dense=[True, True])
br_prediction = binary_rel_clf.predict(X_test)
br_prediction.toarray()

# print(round(accuracy_score(y_test,br_prediction),4))
# print(precision_score(y_test,br_prediction,average='macro',zero_division=0))
# print(f1_score(y_test,br_prediction,average='macro',zero_division=0))
# print(recall_score(y_test,br_prediction,average='macro',zero_division=0))

file_name ='D:/TAI_LIEU/Ky_1_nam_4/Phat_Trien_He_Thong_Thong_Thong_minh/venv/model20231025020736'
with open(file_name,'rb') as file:
    new_model =pickle.load(file)

# i = 40
# # Kiểm tra xem i có nằm trong phạm vi hợp lệ không
# if i >= 0 and i < X_test.shape[0]:
#     # Truy cập kết quả dự đoán cho mẫu kiểm tra này và chuyển đổi thành ma trận thưa (sparse matrix)
#     prediction = new_model.predict(X_test)[i].toarray()
#     # In ra kết quả gợi ý mua hàng
#     print("Gợi ý mua hàng cho mẫu kiểm tra thứ", i)
#     for j, label in enumerate(y.columns):
#         if 1 in prediction[:,j]:
#             print(f"{label}: {prediction[0][j]}")
# else:
#     print("Giá trị i không hợp lệ. Hãy chọn giá trị i trong khoảng từ 0 đến", X_test.shape[0] - 1)

new_sample = df.iloc[400]  # Thay 0 bằng chỉ số hàng mà bạn muốn chọn

# Chuyển đổi mẫu kiểm tra mới thành mảng NumPy (hoặc tương tự)
new_sample_text = new_sample['id_user']  # Biến đổi chuỗi thành danh sách
print(new_sample_text)
new_sample_array = tfidf.transform([new_sample_text]).toarray()  # Sử dụng transform() thay vì fit_transform()

# Sử dụng mẫu kiểm tra mới cho dự đoán
new_prediction = new_model.predict(new_sample_array)
new_prediction = new_prediction.toarray()

# In ra kết quả dự đoán cho mẫu kiểm tra mới
print("Gợi ý mua hàng cho mẫu kiểm tra mới:")
for j, label in enumerate(y.columns):
    if 1 in new_prediction[:, j]:
        print(f"{label}: {new_prediction[0][j]}")



