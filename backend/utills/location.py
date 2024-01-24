import requests
import math


def get_coordinates(address: str, address_number: int, postal_code: str):
    """
    Get the Latitude and Longitude via geocode.maps.co API
    """
    address_name = address.replace(" ", "+")
    api_url = f"https://geocode.maps.co/search?street={address_number}+{address_name}&postalcode={postal_code}"
    response = requests.get(api_url)
    data = response.json()
    try:
        Latitude = data[0]["lat"]
        Longitude = data[0]["lon"]
    except:
        Latitude = 0
        Longitude = 0

    return [Latitude, Longitude]


def calculate_distance(lat1, lon1, lat2, lon2):
    """
    Calculate the distance between two points in kilometers
    """
    R = 6371
    
    lat1, lon1, lat2, lon2 = get_float_coords(lat1, lon1, lat2, lon2)

    dLat = deg2rad(lat2 - lat1)
    dLon = deg2rad(lon2 - lon1)
    a = math.sin(dLat / 2) * math.sin(dLat / 2) + math.cos(deg2rad(lat1)) * math.cos(
        deg2rad(lat2)
    ) * math.sin(dLon / 2) * math.sin(dLon / 2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    distance = R * c
    return distance


def deg2rad(deg):
    return deg * (math.pi / 180)

def get_float_coords(lat1, lon1, lat2, lon2):
    try:
        lat1 = float(lat1)
        lon1 = float(lon1)
        lat2 = float(lat2)
        lon2 = float(lon2)
    except:
        return 0,0,0,0
    
    return lat1, lon1, lat2, lon2
    
