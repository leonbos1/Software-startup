from flask import Flask
from flask_cors import CORS

from .routes.products import products as products_blueprint
from .routes.users import users as users_blueprint

def create_app():
    app = Flask(__name__)
    cors = CORS(app, resources={r"/*": {"origins": "*"}})

    app.register_blueprint(products_blueprint, url_prefix='/products')
    app.register_blueprint(users_blueprint, url_prefix='/users')

    return app
