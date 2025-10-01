import pandas as pd
import pickle
from sklearn.metrics import classification_report, accuracy_score, precision_score, recall_score, f1_score, confusion_matrix
import argparse

def load_pipeline(pipeline_path):
    """
    Load the saved pipeline from a file.
    """
    with open(pipeline_path, 'rb') as f:
        pipeline = pickle.load(f)
    return pipeline

def load_test_data(test_file_path):
    """
    Load the test data from a CSV file.
    """
    return pd.read_csv(test_file_path)

def interpret_results(accuracy, precision, recall, f1, cm):
    """
    Provide a textual interpretation of the evaluation metrics.
    """
    interpretation = []
    interpretation.append(f"Accuracy indicates that {accuracy * 100:.2f}% of all predictions were correct.")
    interpretation.append(f"Precision indicates that {precision * 100:.2f}% of the cases predicted as fraud were actually fraud.")
    interpretation.append(f"Recall indicates that {recall * 100:.2f}% of actual fraud cases were correctly identified by the model.")
    interpretation.append(f"F1 Score balances precision and recall, with a value of {f1:.2f}, indicating overall performance.")

    tp = cm[1, 1]  # True Positives
    fp = cm[0, 1]  # False Positives
    fn = cm[1, 0]  # False Negatives
    tn = cm[0, 0]  # True Negatives

    interpretation.append("\nConfusion Matrix Analysis:")
    interpretation.append(f"True Positives (Fraud correctly identified): {tp}")
    interpretation.append(f"False Positives (Non-fraud wrongly identified as fraud): {fp}")
    interpretation.append(f"False Negatives (Fraud missed as non-fraud): {fn}")
    interpretation.append(f"True Negatives (Non-fraud correctly identified): {tn}")

    total_non_fraud = tn + fp
    total_fraud = tp + fn
    interpretation.append(f"\n{tn} out of {total_non_fraud} non-fraud cases were correctly classified.")
    interpretation.append(f"{tp} out of {total_fraud} fraud cases were correctly classified.")

    return "\n".join(interpretation)

def main():
    parser = argparse.ArgumentParser(description="Evaluate a saved machine learning pipeline.")
    parser.add_argument("pipeline", type=str, help="Path to the saved pipeline file.")
    parser.add_argument("test_file", type=str, help="Path to the test dataset CSV file.")
    args = parser.parse_args()

    pipeline_path = args.pipeline
    test_file_path = args.test_file

    # Load the pipeline
    pipeline = load_pipeline(pipeline_path)
    print("Pipeline loaded successfully.")

    # Load the test data
    test_data = load_test_data(test_file_path)
    print("Test data loaded successfully.")

    # Separate features (X_test) and target (y_test)
    X_test = test_data.drop(columns=['Fraud'])
    y_test = test_data['Fraud']

    # Make predictions
    y_pred = pipeline.predict(X_test)

    # Evaluate and display results
    print("\nEvaluation Metrics:")
    accuracy = accuracy_score(y_test, y_pred)
    precision = precision_score(y_test, y_pred)
    recall = recall_score(y_test, y_pred)
    f1 = f1_score(y_test, y_pred)

    print(f"Accuracy: {accuracy:.4f}")
    print(f"Precision: {precision:.4f}")
    print(f"Recall: {recall:.4f}")
    print(f"F1 Score: {f1:.4f}")

    print("\nConfusion Matrix:")
    cm = confusion_matrix(y_test, y_pred)
    print(cm)

    print("\nClassification Report:")
    report = classification_report(y_test, y_pred, target_names=['Not Fraud', 'Fraud'])
    print(report)

    # Add textual interpretation
    interpretation = interpret_results(accuracy, precision, recall, f1, cm)
    print("\nInterpretation:")
    print(interpretation)

if __name__ == "__main__":
    main()
