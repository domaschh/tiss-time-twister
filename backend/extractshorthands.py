from bs4 import BeautifulSoup
import json

def extract_text_and_create_acronym(html_filename):
    # Read HTML content from a file
    with open(html_filename, 'r') as file:
        html_content = file.read()

    # Parse the HTML content
    soup = BeautifulSoup(html_content, 'html.parser')

    # Find all div elements with the specified class
    div_elements = soup.find_all('div', class_='nodeTable-level-3')
    wahlfaecher = soup.find_all('div', class_='nodeTable-level-4')
    total = div_elements + wahlfaecher
    # Words to skip when creating the acronym
    skip_words = ['in', 'die', 'der', 'und', 'and', 'for', 'für']
    special_words = ['VU', 'VO', 'UE', 'PR', 'SE']  # Special words to handle separately
    years= [ '2023W', '2024S']
    # Process each div element
    extracted_data = []
    for div in set(total):
        full_text = div.get_text(strip=True)
        seperated = full_text.split()
        if seperated[0][0].isnumeric():
            seperated=seperated[1:]
        for spw in special_words:
            if spw in seperated[0] and len(seperated) >=1:
                first = seperated[0].replace(spw, '')
                seperated = [first] + seperated[1:]
        if seperated[0] == '':
            seperated = seperated[1:]

        for spw in years:
            if spw in seperated[0] and len(seperated) >=2:
                first = seperated[0].replace(spw, '')
                seperated = [first] + seperated[1:]

        acronym = ""
        if len(seperated) == 1:
            acronym = seperated[0]
        else:
            acronym = ''.join([first[0] for first in seperated if first not in skip_words])
        print(acronym)
        extracted_data.append({'full_text': full_text, 'shorthand': acronym})

    # Store in a JSON file
    with open('extracted_data.json', 'w') as json_file:
        json.dump(extracted_data, json_file, indent=4)

    print("Data extracted and stored in 'extracted_data.json'")
# Example usage
html_filename = 'bachelor.html'
extract_text_and_create_acronym(html_filename)
