from flask import Blueprint, jsonify, request
from ..extensions import db
from ..models.product import Product
from ..utills.auth import logged_in_required, get_safe_user, get_restricted_user
from ..utills.location import calculate_distance

products = Blueprint("products", __name__)


@products.route("", methods=["GET"])
def get():
    """
    Get all products in firebase
    """
    products_ref = db.collection("products").get()

    products = [product.to_dict() for product in products_ref]
                
    for product in products:
        try:
            product["user"] = get_restricted_user(product["user"])
        except Exception as e:
            print("Product does not have a user")

    return jsonify(products)



@products.route("/radius/<radius>", methods=["GET"])
@logged_in_required
def get_within_radius(current_user, radius):
    """
    Get all products within a radius
    """
    products = db.collection("products").get()
    radius = float(radius)

    for product in products:
        try:
            user_id = product.to_dict()["user"]["id"]

            user = db.collection("users").where("id", "==", user_id).get()

            if len(user) == 0:
                return jsonify({"message": "An error occurred!"}), 500

            user = user[0].to_dict()

            distance = calculate_distance(
                current_user["latitude"],
                current_user["longitude"],
                user["latitude"],
                user["longitude"],
            )

            if distance > radius:
                products.remove(product)

        except Exception as e:
            print(e)

    return jsonify([product.to_dict() for product in products])


@products.route("", methods=["POST"])
@logged_in_required
def post(current_user):
    """
    Create a new product in firebase
    """
    data = request.get_json()

    product = Product(
        name=data["name"],
        description=data["description"],
        expiration_date=data["expiration_date"],
        user=get_safe_user(current_user),
    )

    db.collection("products").add(product.to_dict())

    return jsonify(product.to_dict())


@products.route("/<id>", methods=["GET"])
@logged_in_required
def get_by_id(current_user, id: str):
    """
    Get a single product from firebase
    """
    products_ref = db.collection("products")

    products = products_ref.where("id", "==", id).get()

    if len(products) == 0:
        return jsonify({"message": "Product not found!"}), 404

    return jsonify(products[0].to_dict())


@products.route("/<id>", methods=["DELETE"])
@logged_in_required
def delete(current_user, id: str):
    """
    Remove a product from firebase
    """
    products_ref = db.collection("products")

    products = products_ref.where("id", "==", id).get()

    if len(products) == 0:
        return jsonify({"message": "Product not found!"}), 404

    product = products[0].to_dict()

    if product["user"]["id"] != current_user["id"]:
        return jsonify({"message": "Unauthorized!"}), 401

    products_ref.document(products[0].id).delete()

    return jsonify({"message": "Product deleted!"})
