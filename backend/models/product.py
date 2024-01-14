import datetime
import requests
from .base import Base


class Product(Base):
    name: str
    description: str
    expiration_date: datetime.date
    phone_number: str
    first_name: str
    last_name: str
    email: str
    address: str
    city: str
    postal_code: str
    street: str
    address_number: int
    latitude: float
    longitude: float

    def __init__(self, name, description, expiration_date, phone_number, first_name, last_name, email, address, city, postal_code, street, address_number):
        self.name = name
        self.description = description
        self.expiration_date = expiration_date
        self.phone_number = phone_number
        self.first_name = first_name
        self.last_name = last_name
        self.email = email
        self.address = address
        self.address_number = address_number
        self.city = city
        self.postal_code = postal_code
        geolocation = self.get_coordinates(address, address_number, postal_code)
        self.latitude = geolocation[0]
        self.longitude = geolocation[1]

        Base.__init__(self)

    def __repr__(self):
        return f'<Product {self.name}>'

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'description': self.description,
            'expiration_date': self.expiration_date,
            'phone_number': self.phone_number,
            'first_name': self.first_name,
            'last_name': self.last_name,
            'email': self.email,
            'address': self.address,
            'city': self.city,
            'postal_code': self.postal_code,
            'street': self.street,
            'address_number': self.address_number,
            'latitude': self.latitude,
            'longitude': self.longitude,
            'created': self.created,
            'updated': self.updated
        }
    
    def get_coordinates(address: str, address_number: int, postal_code: str):
        """
        Get the Latitude and Longitude via geocode.maps.co API
        """
        address_name = address.replace(" ", "+")
        api_url = f'https://geocode.maps.co/search?street={address_number}+{address_name}&postalcode={postal_code}'
        response = requests.get(api_url)
        data = response.json()
        Latitude = data[0]['lat']
        Longitude = data[0]['lon']
        return [Latitude, Longitude]
