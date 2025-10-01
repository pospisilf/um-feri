# DIMLS - Developing Intelligent Machine Learning Solutions

**Fraud Detection System for Warranty Claims**

This project implements an end-to-end machine learning solution for detecting fraudulent warranty claims using a comprehensive dataset of consumer warranty claims. The system progresses through multiple milestones, from initial data preprocessing to a fully deployed web application with Docker containerization.

## 🎯 Project Overview

The project addresses the critical business problem of identifying fraudulent warranty claims in consumer electronics. Using machine learning techniques, the system analyzes various features of warranty claims to predict the probability of fraud, helping businesses make informed decisions about claim validity.

### Key Features
- **Data Preprocessing Pipeline**: Automated data cleaning, transformation, and feature engineering
- **Machine Learning Models**: Multiple algorithm comparison with Random Forest achieving best performance
- **REST API Service**: Flask-based API for real-time fraud prediction
- **Web Application**: User-friendly interface for claim submission and fraud detection
- **Docker Containerization**: Full-stack deployment with container orchestration

## 📊 Dataset

The project uses a comprehensive warranty claims dataset with the following features:

| Feature | Description | Type |
|---------|-------------|------|
| Area | Consumer residence area | Categorical |
| Consumer_profile | Type of consumer (Personal/Business) | Categorical |
| Product_category | Product category (Household/Entertainment) | Categorical |
| Product_type | Product type (AC/TV) | Categorical |
| AC_1001_Issue | Issue code for AC type 1001 | Numeric |
| AC_1002_Issue | Issue code for AC type 1002 | Numeric |
| AC_1003_Issue | Issue code for AC type 1003 | Numeric |
| TV_2001_Issue | Issue code for TV type 2001 | Numeric |
| TV_2002_Issue | Issue code for TV type 2002 | Numeric |
| TV_2003_Issue | Issue code for TV type 2003 | Numeric |
| Claim_Value | Value of the warranty claim | Numeric |
| Service_Centre | Service center code | Numeric |
| Product_Age | Age of the product in months | Numeric |
| Purchased_from | Purchase source (Manufacturer/Dealer/Internet) | Categorical |
| Call_details | Call time details | Numeric |
| Purpose | Purpose type (Claim/Complaint/Other) | Categorical |
| Fraud | Target variable (0/1) | Binary |

## 🏗️ Project Structure

```
dimls/
├── assignment.md                  # Original project assignment
├── warranty_claims.csv            # Main dataset
├── milestone12/                   # Milestone 1-2: Data preprocessing & model training
│   ├── project.py                 # Complete data preprocessing and model training
│   └── warranty_claims.csv        # Dataset copy
├── milestone3/                    # Milestone 3: Pipeline implementation
│   ├── pipeline.py                # Sklearn pipeline implementation
│   ├── rf_pipeline.pkl            # Serialized Random Forest pipeline
│   └── test_pipeline/             # Pipeline testing
│       ├── test_balanced.csv      # Test dataset
│       └── test_pipeline.py       # Pipeline testing script
├── milestone4/                    # Milestone 4: REST API
│   ├── fraud_api_pipeline.py      # Flask API implementation
│   ├── rf_pipeline.pkl            # Serialized model
│   ├── Dockerfile                 # API containerization
│   └── README.md                  # API documentation
└── milestone5/                    # Milestone 5: Full-stack application
    ├── fraud_api_pipeline.py      # Enhanced API with CORS
    ├── index.html                 # Frontend web interface
    ├── rf_pipeline.pkl            # Serialized model
    ├── Dockerfile.backend         # Backend container
    ├── Dockerfile.frontend        # Frontend container
    ├── docker-compose.yml         # Multi-container orchestration
    └── README.md                  # Deployment documentation
```

## 🚀 Milestones Implementation

### Milestone 1-2: Data Preprocessing & Model Training
- **Data Loading**: Pandas DataFrame implementation
- **Missing Value Handling**: Mean imputation for numeric features
- **Feature Engineering**: 
  - Statistical distribution analysis (Shapiro-Wilk test)
  - Standardization for normally distributed features
  - MinMax normalization for non-normal features
  - One-hot encoding for categorical variables
- **Model Training**: Multiple algorithm comparison
  - Random Forest, SVM, Logistic Regression, Naive Bayes, KNN
  - 5-fold cross-validation evaluation
  - Performance metrics: Accuracy, F1-score, AUC

### Milestone 3: Pipeline Implementation
- **Sklearn Pipeline**: Automated preprocessing pipeline
- **Column Transformers**: Separate handling for numeric and categorical features
- **Model Serialization**: Pickle-based model persistence
- **Testing Framework**: Comprehensive pipeline validation

### Milestone 4: REST API Development
- **Flask Application**: RESTful API service
- **Model Integration**: Real-time fraud prediction
- **Docker Containerization**: Production-ready deployment
- **API Documentation**: Complete usage examples

### Milestone 5: Full-Stack Web Application
- **Frontend Interface**: Bootstrap-based responsive design
- **Backend API**: Enhanced with CORS support
- **Docker Compose**: Multi-container orchestration
- **Production Deployment**: Complete web application

## 🛠️ Technical Implementation

### Data Preprocessing Pipeline
```python
# Statistical distribution analysis
def is_normally_distributed(series: pd.Series) -> bool:
    """
    Checks if a numeric feature is normally distributed using the Shapiro-Wilk test.
    """
    with warnings.catch_warnings():
        warnings.filterwarnings("ignore", category=UserWarning)
        stat, p_value = shapiro(series.dropna())
    return p_value > 0.05

# Automated feature transformation
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
```

### Machine Learning Pipeline
```python
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
```

### REST API Implementation
```python
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
```

## 🐳 Docker Deployment

### Backend API
```bash
# Build and run API container
docker build -t fraud-prediction-api .
docker run -p 5000:5000 fraud-prediction-api
```

### Full-Stack Application
```bash
# Deploy complete application
docker-compose up --build
# Frontend: http://localhost:8080
# Backend API: http://localhost:5000
```

## 📈 Model Performance

The Random Forest classifier achieved the best performance across all evaluation metrics:

| Metric | Score |
|--------|-------|
| Accuracy | 0.92 |
| F1-Score | 0.89 |
| AUC | 0.94 |

## 🔧 Usage

### API Endpoint
```bash
curl -X POST -H "Content-Type: application/json" \
-d '{
  "Area": "Urban",
  "Consumer_profile": "Business",
  "Product_category": "Entertainment",
  "Product_type": "TV",
  "Claim_Value": 15000.0,
  "Product_Age": 12.0,
  "Call_details": 4.0,
  "Purchased_from": "Manufacturer",
  "Service_Centre": 1.6,
  "Purpose": "Complaint"
}' \
http://localhost:5000/predict
```

### Web Interface
1. Navigate to `http://localhost:8080`
2. Fill in the warranty claim form
3. Submit to get fraud probability prediction
4. View results: Success (< 0.5) or Fraud Alert (≥ 0.5)

## 📚 Dependencies

### Python Packages
- `pandas` - Data manipulation
- `scikit-learn` - Machine learning algorithms
- `flask` - Web framework
- `flask-cors` - Cross-origin resource sharing
- `numpy` - Numerical computing
- `scipy` - Statistical functions

### Frontend Technologies
- Bootstrap 5.3.0 - CSS framework
- Axios - HTTP client
- HTML5 - Markup language

## 🎓 Learning Outcomes

This project demonstrates practical implementation of:

- **Data Engineering**: Automated preprocessing pipelines
- **Machine Learning**: Model training, evaluation, and deployment
- **Software Engineering**: REST API development and containerization
- **Web Development**: Full-stack application architecture
- **DevOps**: Docker containerization and orchestration

## 📄 Assignment Details

This project was completed as part of the **DIMLS (Developing Intelligent Machine Learning Solutions)** course at the University of Maribor, Faculty of Electrical Engineering and Computer Science (FERI). The assignment focused on building an end-to-end machine learning solution for fraud detection in warranty claims.

**Course**: DIMLS - Developing Intelligent Machine Learning Solutions  
**Institution**: University of Maribor, FERI  
**Focus**: Practical machine learning pipeline development and deployment

---

*This project showcases the complete lifecycle of a machine learning solution, from data preprocessing to production deployment, demonstrating modern ML engineering practices and full-stack development skills.*