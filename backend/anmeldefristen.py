import requests
from bs4 import BeautifulSoup

def extract_links_from_file(file_path):
    links = []
    with open(file_path, 'r') as file:
        for line in file:
            parts = line.strip().split(',')
            if parts:
                link = parts[-1]
                links.append(link)
    return links

def fetch_html_from_links(links):
    html_contents = []
    for link in links:
        try:
            response = requests.get(link)
            response.raise_for_status()
            html_contents.append(response.text)
            print(response.text)
        except requests.RequestException as e:
            print(f"Error fetching {link}: {e}")
            html_contents.append(None)
    return html_contents

def extract_dates_from_html(html_contents):
    dates = []
    for html in html_contents:
        if html:
            soup = BeautifulSoup(html, 'lxml')
            label = soup.find('label', {'for': "examDateListForm:j_id_56:j_id_59:0:appBeginn"})
            if label:
                span = label.find_next('span')
                if span and span.get('id') == "examDateListForm:j_id_56:j_id_59:0:appBeginn":
                    dates.append(span.text.strip())
                else:
                    dates.append('Date not found')
            else:
                dates.append('Label not found')
        else:
            dates.append('Invalid HTML content')
    return dates
#
# # Usage
# file_path = 'lvas2.txt'  # Replace with your file path
# links = extract_links_from_file(file_path)
# html_contents = fetch_html_from_links(links)
# dates = extract_dates_from_html(html_contents)
#
# for date in dates:
#     print(date)
from requests_html import HTMLSession

# Your cookies
cookies = {
    'JSESSIONID': 'd6~A534C5EDC6C4174F3B86ADE59506D50D',
    'TISS_AUTH': '927d72afd8cd70a549403725e18d52021a83db327486bfd214b9e7a387552667',
    # Add other cookies here as needed
}

session = HTMLSession()

url = 'https://tiss.tuwien.ac.at/education/course/examDateList.xhtml?dswid=8714&dsrid=90&courseNr=192125&semester=2023W'

# Fetch the page with cookies
response = session.get(url, cookies=cookies)

# Render the page to execute JavaScript
response.html.render()

# Access the content
print(response.html.html)
