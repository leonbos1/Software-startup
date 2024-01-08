from flask import Flask
from flask_cors import CORS

from .routes.products import products as products_blueprint


def create_app():
    app = Flask(__name__)
    cors = CORS(app, resources={r"/*": {"origins": "*"}})

    app.register_blueprint(products_blueprint, url_prefix='/products')

    return app
