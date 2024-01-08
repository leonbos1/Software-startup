import datetime
import uuid


class Base():
    id: int
    created: datetime.datetime
    updated: datetime.datetime

    def __init__(self):
        self.id = str(uuid.uuid4())
        self.created = datetime.datetime.now()
        self.updated = datetime.datetime.now()
