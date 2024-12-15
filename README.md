# Receipt Processor

This is a Spring Boot-based receipt processor application. It processes receipt data, calculates reward points, and exposes REST API endpoints. The application has been dockerized for easy deployment.

---

## Features

- **Process Receipt:** Accepts a receipt JSON and processes it.
- **Points Calculation:** Calculates the reward points based on the total of the receipt.
- **Error Handling:** Returns appropriate error messages for invalid receipts or failed requests.

## Project Structure
```bash
receipt-processor/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── receiptprocessor/
│   │   │   │   │   ├── controller/
│   │   │   │   │   ├── model/
│   │   │   │   │   ├── service/
│   │   │   │   │   ├── model/response/
│   ├── test/
├── pom.xml
├── Dockerfile
└── receipt.json (Sample Request JSON)
```

## Requirements
- **Maven**
- **Java 17 or higher**

OR

(if you do not have Maven or Java installed in your system)

- **Docker** (to containerize and test the application)

## Steps to build the project
### Using Docker

#### 1. Build docker image: (make sure you are in your targeted directory where DockerFile located)
```bash
docker build -t receipt-processor .
```
#### 2. Run the Docker container:
```bash
docker run -p 8080:8080 receipt-processor
```

### Using Maven

#### 1. Build the application
```bash
mvn clean install
```
#### 2. Run the application
```bash
mvn spring-boot:run
```
By following these steps, either using docker or maven, it will start the application, and you can access the API at http://localhost:8080

## API Endpoints
### 1. POST /receipts/process
- This endpoint processes the receipt.
#### Request Body (JSON):
```bash
{
  "retailer": "Store Name",
  "purchaseDate": "2024-12-15",
  "purchaseTime": "14:30",
  "total": "125.00",
  "items": [
    {
      "shortDescription": "Item 1",
      "price": "45.00"
    },
    {
      "shortDescription": "Item 2",
      "price": "80.00"
    }
  ]
}
```
#### Response (JSON):
```bash
{
  "id": "12345-abcdef"
}
```
#### Response Codes:
- **200 OK:** Receipt processed successfully.
- **400 Bad Request:** Invalid receipt data.
- **500 Internal Server Error:** Server error or unexpected failure.

### 2. GET /receipts/{id}/points
- This endpoint fetches the reward points associated with a given receipt ID
#### Example Request:
```bash
GET /receipts/12345-abcdef/points
```
#### Response (JSON):
```bash
{
  "points": 15
}
```
#### Response Codes:
- **200 OK:** Points retrieved successfully.
- **404 Not Found:** Receipt ID not found.

## How to Test the API
### Using curl: (make sure you are in your project root directory)
```bash
curl -X POST http://localhost:8080/receipts/process \
-H "Content-Type: application/json" \
-d @receipt.json
```

### Using Postman:
#### for /receipts/process endpoint
1. Set the request type to **POST**.
2. Enter the URL http://localhost:8080/receipts/process.
3. Under **Body**, select **raw** and **JSON**.
4. Paste your JSON data and hit Send.

#### for /receipts/{id}/points endpoint
1. Set the request type to **GET**.
2. Enter the URL http://localhost:8080/receipts/{id}/process. (use the id generated in previous step)
3. Hit Send.
