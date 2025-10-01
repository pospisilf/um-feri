import pandas as pd
import numpy as np
from typing import List
from scipy.stats import shapiro
from sklearn.preprocessing import StandardScaler, MinMaxScaler, OneHotEncoder
import warnings
from typing import Tuple
from sklearn.model_selection import cross_val_score, KFold
from sklearn.ensemble import RandomForestClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.svm import SVC
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import f1_score, roc_auc_score, make_scorer

def load_data(file_path: str) -> pd.DataFrame:
    """
    Loads the dataset from a CSV file into a pandas DataFrame.
    """
    return pd.read_csv(file_path)

def remove_unnecessary_columns(df: pd.DataFrame, columns_to_remove: List[str]) -> pd.DataFrame:
    """
    Removes unnecessary columns from the DataFrame.
    """
    return df.drop(columns=columns_to_remove)

def fill_missing_values(df: pd.DataFrame) -> pd.DataFrame:
    """
    Fills missing values with mode for categorical and mean for numeric columns.
    """
    for col in df.columns:
        if df[col].dtype == 'object':
            df[col] = df[col].fillna(df[col].mode()[0]) # Most frequently occurring value.
        else:
            df[col] = df[col].fillna(df[col].mean()) # The average value for the column.
    return df

def is_normally_distributed(series: pd.Series) -> bool:
    """
    Checks if a numeric feature is normally distributed using the Shapiro-Wilk test.
    Note: Suppressing warnings for large datasets.
    """
    with warnings.catch_warnings():
        warnings.filterwarnings("ignore", category=UserWarning)
        stat, p_value = shapiro(series.dropna())
    return p_value > 0.05  # If p-value > 0.05, data is assumed to be normally distributed.

def standardize_column(series: pd.Series) -> pd.Series:
    """
    Standardizes a column by applying z-score normalization.
    """
    scaler = StandardScaler()
    return pd.Series(scaler.fit_transform(series.values.reshape(-1, 1)).flatten(), index=series.index)

def normalize_column(series: pd.Series) -> pd.Series:
    """
    Normalizes a column by scaling it to a range between 0 and 1.
    """
    scaler = MinMaxScaler()
    return pd.Series(scaler.fit_transform(series.values.reshape(-1, 1)).flatten(), index=series.index)

def apply_transformation(df: pd.DataFrame, columns_to_transform: List[str]) -> pd.DataFrame:
    """
    Applies standardization or normalization to specified numeric features.
    Only the columns listed in `columns_to_transform` will be processed.
    """
    for col in columns_to_transform:
        if col not in df.columns:
            print(f"Warning: Column {col} not found in the DataFrame. Skipping transformation.")
            continue
        
        if df[col].dtype not in [np.float64, np.float32, np.int64, np.int32]:
            print(f"Warning: Column {col} is not numeric. Skipping transformation.")
            continue
        
        if is_normally_distributed(df[col]):
            print(f"{col} is normally distributed. Applying standardization.")
            df[col] = standardize_column(df[col])
        else:
            print(f"{col} is not normally distributed. Applying normalization.")
            df[col] = normalize_column(df[col])
    return df

def one_hot_encode(df: pd.DataFrame, columns_to_encode: List[str]) -> pd.DataFrame:
    """
    One-hot encodes specified categorical features in the DataFrame, keeping all categories.
    """
    # Check if columns_to_encode are actually in the DataFrame
    categorical_cols = [col for col in columns_to_encode if col in df.columns]
    
    # If there are no valid columns to encode, return the original DataFrame.
    if not categorical_cols:
        print("No categorical columns to encode.")
        return df
    
    # Initialize the OneHotEncoder without dropping any category.
    encoder = OneHotEncoder(drop=None, sparse_output=False)  # Retain all categories.
    
    # Fit and transform the specified categorical columns
    encoded_data = encoder.fit_transform(df[categorical_cols])
    
    # Convert the encoded data to a DataFrame and name columns correctly.
    encoded_df = pd.DataFrame(encoded_data, columns=encoder.get_feature_names_out(categorical_cols), index=df.index)
    
    # Drop the original categorical columns from df and concatenate the new one-hot encoded columns.
    df = df.drop(columns=categorical_cols)
    df = pd.concat([df, encoded_df], axis=1)
    
    print(f"One-hot encoding applied to columns: {categorical_cols}")
    return df

def print_columns(df: pd.DataFrame):
    """
    Prints the list of all column names in the DataFrame.
    """
    print("Columns in the DataFrame:")
    print(df.columns.tolist())

def print_row(df: pd.DataFrame, row_index: int):
    """
    Prints a specified row of the DataFrame in the format:
    column name: value
    """
    if row_index < 0 or row_index >= len(df):
        print(f"Invalid row index: {row_index}")
        return
    
    row = df.iloc[row_index]
    formatted_row = "\n".join([f"{col}: {val}" for col, val in row.items()])
    print(formatted_row)

def split_dataset(df: pd.DataFrame) -> Tuple[pd.DataFrame, pd.Series]:
    """
    Splits the DataFrame into features (X) and target (y).
    The target variable is 'Fraud'.
    """
    # Define X (input features) by removing the target column 'Fraud'
    X = df.drop(columns=['Fraud'])
    
    # Define y (target variable), which is the 'Fraud' column
    y = df['Fraud']
    
    return X, y

def main():
    # Milestone 1: 
    print("Milestone 1 tasks")
    # Load the dataset
    file_path = 'warranty_claims.csv'
    df = load_data(file_path)
    
    # Columns to remove
    columns_to_remove = ['ID'] # Remove only ID! -> based on consultation for 1st milestone.
    
    # Clean the data
    df = remove_unnecessary_columns(df, columns_to_remove)
    df = fill_missing_values(df)
    print("Missing values have been filled.")
    
    # Apply transformations to the specified numeric features
    columns_to_transform = ['Claim_Value', 'Product_Age', 'Call_details', 'Service_Centre']
    df = apply_transformation(df, columns_to_transform)
    
    # One-hot encode categorical features
    columns_to_encode = ['Area', 'Consumer_profile', 'Product_category', 'Product_type', 'Purchased_from', 'Purpose']
    df = one_hot_encode(df, columns_to_encode)
    print("\n")

    # Milestone 2:
    print("Milestone 2 tasks")
    # Split data set into features (X) and target (y)
    X, y = split_dataset(df)  # X = input, y = target (fraud)

    # Print input and target features
    # print("Input features (X):")
    # print(X.head())

    # print("\nTarget feature (y):")
    # print(y.head())

    # Define classification models to evaluate
    models = {
        'Logistic Regression': 
            LogisticRegression(
                max_iter=1000, 
                class_weight='balanced',  # Handle class imbalance
                penalty='l2',             # Regularization (default)
                solver='liblinear',       # Robust for small/imbalanced datasets
                C=1.0                     # Regularization strength; smaller values increase regularization
            ),
        'Random Forest': 
            RandomForestClassifier(
                n_estimators=200,         # More trees (at the cost of computational time)
                max_depth=10,             # Limit depth for better generalization
                min_samples_split=5,      # Minimum samples needed to split a node
                min_samples_leaf=2,       # Minimum samples per leaf node
                class_weight='balanced',  # Handle class imbalance
                random_state=42           # Ensures reproducibility
            ),
        'SVM (Support Vector Machine)': 
            SVC(
                kernel='rbf',             # Radial basis function kernel (default)
                C=1.0,                    # Regularization strength
                gamma='scale',            # Kernel coefficient for 'rbf', 'poly', and 'sigmoid'
                class_weight='balanced',  # Handle class imbalance
                probability=True          # Enable probability estimation for AUC
            ),
        'K-Nearest Neighbors': 
            KNeighborsClassifier(
                n_neighbors=5,            # Number of neighbors to use
                weights='distance',       # Weight by distance to prioritize closer neighbors
                metric='minkowski',       # Distance metric (default)
                p=2                       # p=2 is Euclidean distance
            ),
        'Decision Tree': 
            DecisionTreeClassifier(
                max_depth=10,             # Limit depth to prevent overfitting
                min_samples_split=5,      # Minimum samples needed to split
                min_samples_leaf=2,       # Minimum samples per leaf node
                class_weight='balanced',  # Handle class imbalance
                random_state=42           # Ensures reproducibility
            )
    }

    # Define 5-Fold Cross Validation
    cv = KFold(n_splits=5, shuffle=True, random_state=42)

    # Initialize a dictionary to store the evaluation metrics
    metrics = {
        'Model': [],
        'Accuracy': [],
        'F1-Score': [],
        'AUC': []
    }

    # Evaluate models using 5-Fold Cross-Validation
    for model_name, model in models.items():
        print(f"Evaluating {model_name}...")

        # Accuracy
        accuracy_scores = cross_val_score(model, X, y, cv=cv, scoring='accuracy')
        accuracy_mean = accuracy_scores.mean()

        # F1-Score
        f1_scores = cross_val_score(model, X, y, cv=cv, scoring='f1')
        f1_mean = f1_scores.mean()

        # AUC
        auc_scores = cross_val_score(model, X, y, cv=cv, scoring='roc_auc')
        auc_mean = auc_scores.mean()

        # Store the results in the dictionary
        metrics['Model'].append(model_name)
        metrics['Accuracy'].append(accuracy_mean)
        metrics['F1-Score'].append(f1_mean)
        metrics['AUC'].append(auc_mean)

        print(f"Accuracy: {accuracy_mean:.4f}, F1-Score: {f1_mean:.4f}, AUC: {auc_mean:.4f}")
        print("-" * 50)

    # Convert the metrics dictionary to a DataFrame for easier plotting
    metrics_df = pd.DataFrame(metrics)

    # Get best models based on individual score
    best_model_f1 = metrics_df.loc[metrics_df['F1-Score'].idxmax()]
    best_model_accuracy = metrics_df.loc[metrics_df['Accuracy'].idxmax()]
    best_model_auc = metrics_df.loc[metrics_df['AUC'].idxmax()]

    # Print out the results
    print("Model Comparison Results:")
    print("Best Model by F1-Score:", best_model_f1['Model'], "with F1-Score =", best_model_f1['F1-Score'])
    print("Best Model by Accuracy:", best_model_accuracy['Model'], "with Accuracy =", best_model_accuracy['Accuracy'])
    print("Best Model by AUC:", best_model_auc['Model'], "with AUC =", best_model_auc['AUC'])

    # Weighted approach to combine multiple metrics
    metrics_df['score'] = (0.5 * metrics_df['F1-Score']) + (0.3 * metrics_df['Accuracy']) + (0.2 * metrics_df['AUC'])

    # Select the model with the highest combined score
    best_model_combined = metrics_df.loc[metrics_df['score'].idxmax()]

    print(f"The best model based on combined score is: {best_model_combined['Model']} with combined score = {best_model_combined['score']:.4f}")
    print("\n")

if __name__ == "__main__":
    main()