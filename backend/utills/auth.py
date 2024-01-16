from functools import wraps
from flask import request
from flask_restful import abort
from ..models.user import User
from ..extensions import db
import secrets


def logged_in_required(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        token = request.headers.get("Authorization")

        if not token:
            abort(401, message="Authorization header required")

        user = db.collection("users").where("token", "==", token).get()
        if len(user) == 0:
            abort(401, message="Invalid token")

        user = user[0].to_dict()
        user['token'] = token

        if not user:
            abort(401, message="Invalid token")

        return func(user, *args, **kwargs)

    return wrapper


def generate_token() -> str:
    """
    Generate a random token
    """
    return secrets.token_hex(16)


def get_safe_user(user) -> dict:
    """
    Return a safe user dictionary
    """
    return {
        'id': user['id'],
        'first_name': user['first_name'],
        'last_name': user['last_name'],
        'email': user['email'],
        'phone_number': user['phone_number'],
        'created' : user['created'],
        'updated' : user['updated']
    }
