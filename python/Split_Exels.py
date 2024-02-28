import pandas as pd

# Đọc file txt và chia dòng thành các cột tại dấu "|"
with open('D:/ADS/LQWMQRYOJO.txt', 'r') as file:
    lines = file.readlines()

data = [line.strip().split('|') for line in lines]

# Tạo DataFrame từ dữ liệu
df = pd.DataFrame(data, columns=['Email', 'Password'])

# Xuất DataFrame vào file Excel
df.to_excel('D:/ADS/output.xlsx', index=False)