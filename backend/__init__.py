from flask import Flask
from flask_cors import CORS

from .routes.test_route import test_route as test

def create_app():
    app = Flask(__name__)
    cors = CORS(app, resources={r"/*": {"origins": "*"}})

    app.register_blueprint(test, url_prefix='/test')

    return app