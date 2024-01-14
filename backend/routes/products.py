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
        address_number=data['address_number'],
        city=data['city'],
        postal_code=data['postal_code']
    )

    db.collection("products").add(product.to_dict())

    return jsonify({'message': 'Product created!'})


@products.route('/<id>', methods=['GET'])
def get_by_id(id: str):
    """
    Get a single product from firebase
    """
    products_ref = db.collection("products")

    products = products_ref.where("id", "==", id).get()

    if len(products) == 0:
        return jsonify({'message': 'Product not found!'}), 404

    return jsonify(products[0].to_dict())


@products.route('/<id>', methods=['DELETE'])
def delete(id: str):
    """
    Remove a product from firebase
    """
    products_ref = db.collection("products")

    products = products_ref.where("id", "==", id).get()

    if len(products) == 0:
        return jsonify({'message': 'Product not found!'}), 404

    products_ref.document(products[0].id).delete()

    return jsonify({'message': 'Product deleted!'})

