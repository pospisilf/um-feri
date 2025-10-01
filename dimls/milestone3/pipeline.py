import pandas as pd
import numpy as np
import pickle
import re
from typing import List, Tuple
from sklearn.pipeline import Pipeline
from sklearn.compose import ColumnTransformer
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler, MinMaxScaler, OneHotEncoder
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split, StratifiedKFold, cross_val_score
from sklearn.metrics import f1_score, roc_auc_score
from scipy.stats import shapiro
import warnings

# Helper Functions
def is_normally_distributed(series: pd.Series) -> bool:
    """
    Checks if a numeric feature is normally distributed using the Shapiro-Wilk test.
    """
    with warnings.catch_warnings():
        warnings.filterwarnings("ignore", category=UserWarning)
        stat, p_value = shapiro(series.dropna())
    return p_value > 0.05

def identify_issue_columns(df: pd.DataFrame) -> List[str]:
    """
    Identifies all columns that match the pattern for issue codes (e.g., AC_*_Issue, TV_*_Issue).
    """
    return [col for col in df.columns if re.match(r'^(AC|TV)_\d+_Issue$', col)]

# Main Pipeline
def load_data(file_path: str) -> pd.DataFrame:
    """
    Loads the dataset from a CSV file into a pandas DataFrame.
    """
    return pd.read_csv(file_path)

def split_dataset(df: pd.DataFrame) -> Tuple[pd.DataFrame, pd.Series]:
    """
    Splits the DataFrame into features (X) and target (y).
    The target variable is 'Fraud'.
    """
    X = df.drop(columns=['Fraud'])
    y = df['Fraud']
    return X, y

def main():
    file_path = 'warranty_claims.csv'
    df = load_data(file_path)

    # Identify columns for processing
    numeric_features = ['Claim_Value', 'Product_Age', 'Call_details', 'Service_Centre']
    issue_columns = identify_issue_columns(df)
    categorical_features = ['Area', 'Consumer_profile', 'Product_category', 'Product_type', 'Purchased_from', 'Purpose']

    # Preprocessing for numeric features
    numeric_transformer = Pipeline(steps=[
        ('imputer', SimpleImputer(strategy='mean')),
        ('scaler', StandardScaler())
    ])

    # Preprocessing for issue columns (no scaling)
    issue_transformer = Pipeline(steps=[
        ('imputer', SimpleImputer(strategy='most_frequent'))
    ])

    # Preprocessing for categorical features
    categorical_transformer = Pipeline(steps=[
        ('imputer', SimpleImputer(strategy='most_frequent')),
        ('onehot', OneHotEncoder(handle_unknown='ignore'))
    ])

    # Combine preprocessors in a column transformer
    preprocessor = ColumnTransformer(
        transformers=[
            ('num', numeric_transformer, numeric_features),
            ('issues', issue_transformer, issue_columns),
            ('cat', categorical_transformer, categorical_features)
        ]
    )

    # Create a pipeline with preprocessing and classifier
    rf_pipeline = Pipeline(steps=[
        ('preprocessor', preprocessor),
        ('classifier', RandomForestClassifier(
                n_estimators=200,
                max_depth=10,
                min_samples_split=5,
                min_samples_leaf=2,
                class_weight='balanced',
                random_state=42
        ))
    ])

    # Split the dataset
    X, y = split_dataset(df)

    # Stratified 5-Fold Cross-Validation
    cv = StratifiedKFold(n_splits=5, shuffle=True, random_state=42)
    metrics = {'Model': [], 'F1-Score': [], 'AUC': []}

    for train_idx, test_idx in cv.split(X, y):
        X_train, X_test = X.iloc[train_idx], X.iloc[test_idx]
        y_train, y_test = y.iloc[train_idx], y.iloc[test_idx]

        rf_pipeline.fit(X_train, y_train)
        y_pred = rf_pipeline.predict(X_test)
        
        f1 = f1_score(y_test, y_pred)
        auc = roc_auc_score(y_test, rf_pipeline.predict_proba(X_test)[:, 1])
        
        metrics['F1-Score'].append(f1)
        metrics['AUC'].append(auc)

    print(f"Average F1-Score: {np.mean(metrics['F1-Score']):.4f}")
    print(f"Average AUC: {np.mean(metrics['AUC']):.4f}")

    # Train the pipeline on the entire dataset
    rf_pipeline.fit(X, y)

    # Serialize the pipeline using pickle
    with open('rf_pipeline.pkl', 'wb') as f:
        pickle.dump(rf_pipeline, f)

    print("Pipeline has been trained and saved as 'rf_pipeline.pkl'.")

if __name__ == "__main__":
    main()
