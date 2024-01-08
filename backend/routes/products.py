from flask import Blueprint, jsonify, request
from ..extensions import db
from ..models.product import Product

products = Blueprint('products', __name__)


@products.route('', methods=['GET'])
def get():
    """
    Get all products in firebase
    """
    products = db.collection("products").get()

    return jsonify([product.to_dict() for product in products])


@products.route('', methods=['POST'])
def post():
    """
    Create a new product in firebase
    """
    data = request.get_json()

    product = Product(
        name=data['name'],
        description=data['description'],
        expiration_date=data['expiration_date'],
        phone_number=data['phone_number'],
        first_name=data['first_name'],
        last_name=data['last_name'],
        email=data['email'],
        address=data['address'],
        city=data['city']
    )

    db.collection("products").add(product.to_dict())

    return jsonify({'message': 'Product created!'})


@products.route('/<id>', methods=['GET'])
def get_by_id(id):
    """
    Get a single product from firebase
    """
    product = db.collection("products").where("id", "==", id).get()[0]

    return jsonify(product.to_dict())
