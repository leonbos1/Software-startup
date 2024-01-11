from flask import Blueprint, jsonify, request
from ..extensions import db
from ..models.product import Product
from ..utills.auth import logged_in_required, get_safe_user

products = Blueprint('products', __name__)


@products.route('', methods=['GET'])
def get():
    """
    Get all products in firebase
    """
    products = db.collection("products").get()

    return jsonify([product.to_dict() for product in products])


@products.route('', methods=['POST'])
@logged_in_required
def post(current_user):
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
        city=data['city'],
        user=get_safe_user(current_user)
    )

    db.collection("products").add(product.to_dict())

    return jsonify(product.to_dict())


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
@logged_in_required
def delete(current_user, id: str):
    """
    Remove a product from firebase
    """
    products_ref = db.collection("products")

    products = products_ref.where("id", "==", id).get()

    if len(products) == 0:
        return jsonify({'message': 'Product not found!'}), 404

    product = products[0].to_dict()

    if product['user']['id'] != current_user['id']:
        return jsonify({'message': 'Unauthorized!'}), 401

    products_ref.document(products[0].id).delete()

    return jsonify({'message': 'Product deleted!'})
