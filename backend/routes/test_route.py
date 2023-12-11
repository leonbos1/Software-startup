from flask import Blueprint, jsonify, request
from ..extensions import db

test_route = Blueprint('test_route', __name__)


@test_route.route('', methods=['GET'])
def test():
    return jsonify({'message': 'Hello World!'})


@test_route.route('/add', methods=['GET'])
def add():
    """
    Example of how data can be added to firestore
    """

    doc_ref = db.collection("users").document("alovelace")
    doc_ref.set({"first": "Ada", "last": "Lovelace", "born": 1815})

    return jsonify({'message': 'Data added!'})


@test_route.route('/get', methods=['GET'])
def get():
    """
    Example of how data can be retrieved from firestore
    """

    users_ref = db.collection("users")
    docs = users_ref.stream()

    for doc in docs:
        print(f'{doc.id} => {doc.to_dict()}')

    return jsonify({'message': 'Data retrieved!'})
