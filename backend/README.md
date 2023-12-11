1:

Make sure you have the google-credentials.json file and your .env file is filled in correctly.
(ask Leon for the googl credentials)
The .env and google-credentials.json both have to be in ./backend

2:

Make sure you are in the ./backend directory

python -m venv ./venv

3:

.\venv\Scripts\activate

4:

pip -r install requirements.txt

5:

flask --app . run --reload