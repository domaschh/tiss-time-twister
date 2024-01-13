from bs4 import BeautifulSoup

def parse_html(html_content):
    soup = BeautifulSoup(html_content, "html.parser")
    tbody = soup.find('tbody', id="courseList:courseTable_data")
    if not tbody:
        return []

    extracted_data = []
    for row in tbody.find_all('tr', role="row"):
        cells = row.find_all('td', role="gridcell")
        if len(cells) >= 7:
            lva_number = cells[0].get_text(strip=True)
            lva_type = cells[1].get_text(strip=True)
            lva_name = cells[2].get_text(strip=True)
            year_semester = cells[5].get_text(strip=True)

            url = f"https://tiss.tuwien.ac.at/education/course/examDateList.xhtml?courseNr={lva_number.replace('.', '')}&semester={year_semester}"
            extracted_data.append(f"{lva_number},{lva_type},{lva_name},{url}")

    return extracted_data

# Read HTML content from lvas.html
with open('lvas.html', 'r', encoding='utf-8') as file:
    html_content = file.read()

# Parse the HTML and get the data
data = parse_html(html_content)

# Write the data to lvas.txt
with open('lvas.txt', 'w', encoding='utf-8') as txt_file:
    for line in data:
        txt_file.write(line + '\n')

print("Data has been written to lvas.txt")
