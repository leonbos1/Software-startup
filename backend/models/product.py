import datetime
from .base import Base
from .user import User


class Product(Base):
    name: str
    description: str
    expiration_date: datetime.date
    user: User

    def __init__(self, name, description, expiration_date, phone_number, first_name, last_name, email, address, city, user):
        self.name = name
        self.description = description
        self.expiration_date = expiration_date
        self.user = user

        Base.__init__(self)

    def __repr__(self):
        return f'<Product {self.name}>'

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.name,
            'description': self.description,
            'expiration_date': self.expiration_date,
            'user': self.user,
            'created': self.created,
            'updated': self.updated
        }

    def from_dict(data):
        return Product(
            name=data['name'],
            description=data['description'],
            expiration_date=data['expiration_date'],
            user=data['user']
        )
