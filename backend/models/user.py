import datetime
from .base import Base


class User(Base):
    first_name: str
    last_name: str
    email: str
    phone_number: str
    username: str
    password: str
    token: str

    def __init__(self, first_name, last_name, email, phone_number, username, password):
        self.first_name = first_name
        self.last_name = last_name
        self.email = email
        self.phone_number = phone_number
        self.username = username
        self.password = password
        self.token = None

        Base.__init__(self)

    def to_dict(self):
        return {
            'id': self.id,
            'first_name': self.first_name,
            'last_name': self.last_name,
            'email': self.email,
            'phone_number': self.phone_number,
            'username': self.username,
            'password': self.password,
            'created': self.created,
            'updated': self.updated,
        }