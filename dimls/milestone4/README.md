build: 

```shell
docker build -t fraud-prediction-api .
```

run: 
```shell
docker run -p 5000:5000 fraud-prediction-api
```

api call example: 
```shell
curl -X POST -H "Content-Type: application/json" \
-d '{
  "Area": "Urban",
  "Consumer_profile": "Business",
  "Product_category": "Entertainment",
  "Product_type": "TV",
  "AC_1001_Issue": 0.0,
  "AC_1002_Issue": 0.0,
  "AC_1003_Issue": 0.0,
  "TV_2001_Issue": 1.0,
  "TV_2002_Issue": 1.0,
  "TV_2003_Issue": 1.0,
  "Claim_Value": 15000.0,
  "Product_Age": 12.0,
  "Call_details": 4.0,
  "Purchased_from": "Manufacturer",
  "Service_Centre": 1.6,
  "Purpose": "Complaint",
  "Fraud": 1
}' \
http://localhost:5000/predict
```