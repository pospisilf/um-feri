from flask import Flask, request, jsonify
from flask_cors import CORS
import pickle
import pandas as pd
import re

# Initialize Flask app and enable CORS
app = Flask(__name__)
CORS(app)  # Enable CORS for all routes

# Load the trained pipeline
with open('rf_pipeline.pkl', 'rb') as f:
    rf_pipeline = pickle.load(f)

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Log received input
        input_data = request.get_json()
        print("Received Input Data:", input_data)

        # Convert input JSON into DataFrame
        input_df = pd.DataFrame([input_data])
        print("Input DataFrame:\n", input_df)

        # Define required features
        required_numeric = ['Claim_Value', 'Product_Age', 'Call_details', 'Service_Centre']
        required_categorical = ['Area', 'Consumer_profile', 'Product_category', 'Product_type',
                                'Purchased_from', 'Purpose']
        dynamic_issue_columns = [col for col in input_df.columns if re.match(r'^(AC|TV)_\\d+_Issue$', col)]
        required_features = required_numeric + required_categorical + dynamic_issue_columns

        # Check for missing features
        missing_features = [feature for feature in required_features if feature not in input_df.columns]
        if missing_features:
            error_message = f"Missing required features: {', '.join(missing_features)}"
            print("Error:", error_message)
            return jsonify({"error": error_message}), 400

        # Predict fraud probability
        fraud_probability = rf_pipeline.predict_proba(input_df)[:, 1][0]
        print("Fraud Probability:", fraud_probability)

        return jsonify({"fraud_probability": fraud_probability})

    except Exception as e:
        error_message = f"Unexpected error occurred: {str(e)}"
        print("Error:", error_message)
        return jsonify({"error": error_message}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
