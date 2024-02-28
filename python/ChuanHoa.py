import datetime
import re

def time_to_string():
    current_datetime = datetime.datetime.now()
    formatted_datetime = current_datetime.strftime("%Y-%m-%d %H-%M-%S")
    numbers_only = re.sub(r'\D', '', formatted_datetime)
    return numbers_only
