Setting Up and Running the Flask Application

Follow these steps to set up and run your Flask application:

# Prerequisites

    Ensure you have Python installed on your system.
    You need the google-credentials.json file for Google Cloud authentication. Contact Leon to obtain this file.

# Configuration Files

    Place the google-credentials.json file and the .env file in the ./backend directory.
        The .env file should be properly filled with the required environment variables.
        The google-credentials.json is needed for Google Cloud services authentication.

# Setting Up the Environment

    Open a terminal or command prompt and navigate to the ./backend directory:

    cd ./backend

    Create a Python virtual environment in the current directory:

    python -m venv ./venv

    Activate the virtual environment:

        On Windows:
            .\venv\Scripts\activate

        On Unix or MacOS:
            source ./venv/bin/activate

    Install the required Python packages from requirements.txt:
        pip install -r requirements.txt

# Running the Flask Application

    Run the Flask application with the following command:
        flask --app . run --reload

    The --reload flag enables the debugger and auto-reloads the server on code changes.

# Running backend in Docker
    Build an image:
        docker image build . --tag={name}:latest

        docker run -p 5000:5000 

# Deploying to Google Cloud

    Build the image

    from ./backend run:
        docker image build . --tag={name}:latest

    run:
        gcloud builds submit --tag gcr.io/saveplate-backend/{name}:latest
