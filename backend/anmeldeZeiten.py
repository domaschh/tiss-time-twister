import re
import requests
from bs4 import BeautifulSoup
import json

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
    'Accept-Language': 'en-US,en;q=0.5',
    'Referer': 'https://www.google.com/'
}
def extract_anmeldefrist(html_content, lva_name):
    soup = BeautifulSoup(html_content, 'html.parser')
    h2_pruefungen = soup.find('h2', string='Exams')
    if h2_pruefungen:
        next_div = h2_pruefungen.find_next_sibling('div')
        if next_div:
            table = next_div.find('table')
            if table:
                headers = [th.text.strip() for th in table.findAll('th')]
                anmeldefrist_index = headers.index('Application time') if 'Application time' in headers else -1
                exam_index = headers.index('Exam') if 'Exam' in headers else -1

                if anmeldefrist_index != -1 and exam_index != -1:
                    exam_data = []
                    for row in table.find_all('tr')[1:]:  # Skipping header row
                        cells = row.find_all('td')
                        exam_info = {
                            'lva_name': lva_name,
                            'Application time': cells[anmeldefrist_index].text.strip(),
                            'Exam': cells[exam_index].text.strip()
                        }
                        exam_data.append(exam_info)
                    return exam_data
    return []

url = "https://tiss.tuwien.ac.at/course/courseDetails.xhtml?dswid=7604&dsrid=699&courseNr={}&semester=2023W"
def generate_url(course_nr):
    base_url = "https://tiss.tuwien.ac.at/course/courseDetails.xhtml?dswid=7604&dsrid=699&courseNr={}&semester=2023W"
    return base_url.format(course_nr)

headers = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7',
    'Accept-Encoding': 'gzip, deflate, br',
    'Accept-Language': 'en-US,en;q=0.9',
    'Connection': 'keep-alive',
    'Cookie': 'JSESSIONID=d9~19088F97EA509318FACBB090E486DEC9; nmstat=56b61e09-299c-f326-7528-dcedd1f846ec; TISS_LANG=en; _tiss_session=787da7d700a7ddcfbe06774f9c2266dc; dsrwid-379=7604; dsrwid-699=7604',
    'Host': 'tiss.tuwien.ac.at',
    'Referer': 'https://tiss.tuwien.ac.at/course/courseDetails.xhtml?courseNr=185A91&semester=2024S',
    'Sec-Ch-Ua': '"Not_A Brand";v="8", "Chromium";v="120", "Google Chrome";v="120"',
    'Sec-Ch-Ua-Mobile': '?0',
    'Sec-Ch-Ua-Platform': '"macOS"',
    'Sec-Fetch-Dest': 'document',
    'Sec-Fetch-Mode': 'navigate',
    'Sec-Fetch-Site': 'same-origin',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36'
}

lvas = []
lva_url_base = "https://tiss.tuwien.ac.at/course/educationDetails.xhtml?semester=2023S&courseNr="
with open('exam_registrations.txt', 'w') as txt_file, open('lvasthissemester.txt', 'r') as file:
    all_exam_registers = []  # List to store all exam registration data

    for line in file:
        split = line.split(" ")
        course_url = generate_url(split[0].replace(".", ""))
        page = requests.get(course_url, headers=headers)
        lva_name = " ".join(split[3:len(split)]).replace("\n", "")
        exam_registers = extract_anmeldefrist(page.text, lva_name)
        unique_exams = {}

        for entry in exam_registers:
            exam_name = entry['Exam']
            if exam_name not in unique_exams:
                unique_exams[exam_name] = entry

        unique_exam_registers = list(unique_exams.values())
        all_exam_registers.extend(unique_exam_registers)

    # Write each dictionary as a comma-separated line in the text file
    for register in all_exam_registers:
        line = ', '.join([register['lva_name'], register['Application time'], register['Exam']])
        txt_file.write(line + '\n')
