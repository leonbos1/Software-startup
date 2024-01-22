from flask import Blueprint, jsonify, request
from ..extensions import db
from ..models.user import User
from ..utills.auth import logged_in_required, generate_token, get_safe_user
from werkzeug.security import generate_password_hash, check_password_hash

users = Blueprint('users', __name__)


@users.route('', methods=['POST'])
def post():
    """
    Create a new user in firebase
    """
    data = request.get_json()

    try:
        user = User(
            first_name=data['firstName'],
            last_name=data['lastName'],
            email=data['email'],
            phone_number=data['phoneNumber'],
            username=data['userName'],
            password=generate_password_hash(data['password'], method='scrypt')
        )

    except:
        return jsonify({'message': 'Invalid request!'}), 400

    username_exists = db.collection("users").where(
        "username", "==", user.username).get()

    if len(username_exists) > 0:
        return jsonify({'message': 'Username already exists!'}), 400

    db.collection("users").add(user.to_dict())

    return jsonify(user.to_dict())


@users.route('/current_user', methods=['GET'])
@logged_in_required
def get_current_user(current_user):
    """
    Get the current user from firebase
    """
    user = get_safe_user(current_user)
    
    return jsonify(user)


@users.route('/<id>', methods=['GET'])
@logged_in_required
def get_by_id(current_user: User, id: str):
    """
    Get a single user from firebase
    """
    users_ref = db.collection("users")

    users = users_ref.where("id", "==", id).get()

    if len(users) == 0:
        return jsonify({'message': 'User not found!'}), 404

    return jsonify(users[0].to_dict())


@users.route('/<id>', methods=['DELETE'])
@logged_in_required
def delete(current_user, id: str):
    """
    Remove a user from firebase
    """
    users_ref = db.collection("users")

    users = users_ref.where("id", "==", id).get()

    if len(users) == 0:
        return jsonify({'message': 'User not found!'}), 404

    user = users[0].to_dict()

    if user['id'] != current_user['id']:
        return jsonify({'message': 'User not found!'}), 404

    db.collection("users").document(users[0].id).delete()

    return jsonify({'message': 'User deleted!'})


@users.route('/login', methods=['POST'])
def login():
    """
    Login a user
    """
    data = request.get_json()
    try:
        username = data["userName"]
        password = data["password"]
    except:
        return jsonify({'message': 'Invalid request!, userName and password are required'}), 400

    users_ref = db.collection("users")

    users = users_ref.where("username", "==", username).get()

    if len(users) == 0:
        return jsonify({'message': 'User not found!'}), 404

    user = users[0]

    userObject = user.to_dict()

    if not check_password_hash(userObject['password'], password):
        return jsonify({'message': 'Invalid password!'}), 400

    token = generate_token()

    userObject['token'] = token

    users_ref.document(user.id).update(userObject)

    return jsonify({'token': token})


@users.route('/logout', methods=['POST'])
@logged_in_required
def logout(current_user):
    """
    Logout a user
    """
    users_ref = db.collection("users")

    users = users_ref.where("token", "==", current_user['token']).get()

    if len(users) == 0:
        return jsonify({'message': 'User not found!'}), 404

    user = users[0]

    userObject = user.to_dict()

    userObject['token'] = None

    users_ref.document(user.id).update(userObject)

    return jsonify({'message': 'User logged out!'})


@users.route('/<id>', methods=['PUT'])
@logged_in_required
def put(current_user, id: str):
    """
    Update a user in firebase
    """
    data = request.get_json()

    users_ref = db.collection("users")

    users = users_ref.where("id", "==", id).get()

    if len(users) == 0:
        return jsonify({'message': 'User not found!'}), 404

    user = users[0]

    userObject = user.to_dict()

    if userObject['id'] != current_user['id']:
        return jsonify({'message': 'User not found!'}), 404

    userObject['first_name'] = data['first_name']
    userObject['last_name'] = data['last_name']
    userObject['email'] = data['email']
    userObject['phone_number'] = data['phone_number']
    userObject['username'] = data['username']
    userObject['password'] = generate_password_hash(
        data['password'], method='scrypt')

    users_ref.document(user.id).update(userObject)

    return jsonify(userObject)
