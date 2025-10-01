from flask import Flask, request, jsonify
import pickle
import pandas as pd
import re

# Load the trained pipeline
with open('rf_pipeline.pkl', 'rb') as f:
    rf_pipeline = pickle.load(f)

# Define the Flask application
app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    """
    Endpoint for predicting the probability of warranty claim fraud.
    Expects input as JSON.
    """
    try:
        # Parse input JSON
        input_data = request.get_json()

        # Convert input JSON into a DataFrame
        input_df = pd.DataFrame([input_data])

        # Identify all required features, including dynamically detected issue columns
        required_numeric = ['Claim_Value', 'Product_Age', 'Call_details', 'Service_Centre']
        required_categorical = ['Area', 'Consumer_profile', 'Product_category', 'Product_type',
                                'Purchased_from', 'Purpose']

        # Dynamically identify issue columns based on patterns
        dynamic_issue_columns = [col for col in input_df.columns if re.match(r'^(AC|TV)_\d+_Issue$', col)]

        # Combine all required features
        required_features = required_numeric + required_categorical + dynamic_issue_columns

        # Check if all necessary features are present
        missing_features = [feature for feature in required_features if feature not in input_df.columns]
        if missing_features:
            return jsonify({"error": f"Missing features: {', '.join(missing_features)}"}), 400

        # Predict the probability of fraud
        fraud_probability = rf_pipeline.predict_proba(input_df)[:, 1][0]

        # Return the prediction as JSON
        return jsonify({"fraud_probability": fraud_probability})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
