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
    roomNames = soup.find_all('td', class_='Has-name smwtype_txt')
    addresses = soup.find_all('td', class_='Has-address smwtype_txt')

    extracted_data =  []
    for room, address in zip(roomNames, addresses):
        room_text = room.get_text(strip=True)
        address_text = address.get_text(strip=True)
        extracted_data.append({'roomName': room_text, 'address': address_text})

    # Store in a JSON file
    with open('extracted_data_rooms.json', 'w') as json_file:
        json.dump(extracted_data, json_file, indent=4)

    print("Data extracted and stored in 'extracted_data.json'")
# Example usage
html_filename = 'rooms.html'
extract_text_and_create_acronym(html_filename)
