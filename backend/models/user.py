import datetime
import requests
from .base import Base
from ..utills import location


class User(Base):
    first_name: str
    last_name: str
    email: str
    phone_number: str
    username: str
    password: str
    address: str
    address_number: int
    city: str
    postal_code: str
    latitude: float
    longitude: float
    token: str

    def __init__(
        self,
        first_name,
        last_name,
        email,
        phone_number,
        username,
        password,
        address,
        address_number,
        city,
        postal_code,
    ):
        self.first_name = first_name
        self.last_name = last_name
        self.email = email
        self.phone_number = phone_number
        self.username = username
        self.password = password
        self.address = address
        self.address_number = address_number
        self.city = city
        self.postal_code = postal_code
        geolocation = location.get_coordinates(address, address_number, postal_code)
        self.latitude = geolocation[0]
        self.longitude = geolocation[1]
        self.token = None

        Base.__init__(self)

    def to_dict(self):
        return {
            "id": self.id,
            "first_name": self.first_name,
            "last_name": self.last_name,
            "email": self.email,
            "phone_number": self.phone_number,
            "username": self.username,
            "password": self.password,
            "address": self.address,
            "address_number": self.address_number,
            "city": self.city,
            "postal_code": self.postal_code,
            "latitude": self.latitude,
            "longitude": self.longitude,
            "created": self.created,
            "updated": self.updated,
        }
