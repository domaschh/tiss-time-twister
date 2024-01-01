from bs4 import BeautifulSoup
import json
import re
def extract_text_and_create_acronym(html_filename):
    # Read HTML content from a file
    with open(html_filename, 'r') as file:
        html_content = file.read()

    # Parse the HTML content
    soup = BeautifulSoup(html_content, 'html.parser')

    # Find all div elements with the specified class
    div_elements = soup.find_all('div', class_='nodeTable-level-0')
    total = div_elements
    # Words to skip when creating the acronym
    skip_words = ['in', 'die', 'der', 'und', 'and', 'for', 'für']
    special_words = ['VU', 'VO', 'UE', 'PR', 'SE']  # Special words to handle separately
    years= [ '2023W', '2024S']
    # Process each div element
    extracted_data = []
    print(len(div_elements))
    for div in set(total):
        full_text = div.get_text(strip=True)
        full_text = re.sub('[^A-Za-z0-9ÜÄÖüäö]+', ' ', full_text)
        seperated = full_text.split()
        acronym = ""
        if len(seperated) == 2:
            acronym = seperated[0] + ' ' + seperated[1]
        else:
            acronym = seperated[0] + ' ' + ''.join([c[0] for c in seperated[1:] if c not in skip_words])
        print(full_text)
        print(acronym)

        extracted_data.append(full_text + ',' + acronym)

    # Store in a JSON file
    with open('extracted_data.txt', 'w') as file:
        for item in extracted_data:
                file.write(item + '\n')

    print("Data extracted and stored in 'extracted_data.json'")
# Example usage
html_filename = 'bachelor.html'
extract_text_and_create_acronym(html_filename)
