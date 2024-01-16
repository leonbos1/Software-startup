import datetime
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

    def __init__(self, name, description, expiration_date, phone_number, first_name, last_name, email, address, city):
        self.name = name
        self.description = description
        self.expiration_date = expiration_date
        self.phone_number = phone_number
        self.first_name = first_name
        self.last_name = last_name
        self.email = email
        self.address = address
        self.city = city

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
            'created': self.created,
            'updated': self.updated
        }
