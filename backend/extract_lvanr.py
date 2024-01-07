import requests as re
from bs4 import BeautifulSoup

with open("bachelor.html", "r") as bachelor, open("lvasthissemester.txt", "w") as output_file:
    soup = BeautifulSoup(bachelor.read(), 'html.parser')
    divs = soup.find_all('div', class_='nodeTable-level-4')
    divs2 = soup.find_all('div', class_='nodeTable-level-5')
    total =divs + divs2
    texts = [div.get_text(strip=True) for div in total]

    for text in texts:
        if text[0].isdigit():
            formatted_text = text.replace("2023W", "2023W ").replace("2024S", "2024S ")
            output_file.write(formatted_text + '\n')

# The file lvasthissemester.txt will now contain the extracted texts, each on a new line
