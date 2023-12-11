from firebase_admin import credentials, firestore, initialize_app

GOOGLE_APPLICATION_CREDENTIALS = './google-credentials.json'

cred = credentials.Certificate(GOOGLE_APPLICATION_CREDENTIALS)

firebase_app = initialize_app(cred)

db = firestore.client()