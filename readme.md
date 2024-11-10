# Dynamic PDF Generation API
This is a Spring Boot application that generates PDF invoices based on provided data using the Thymeleaf template engine and Flying Saucer (iTextRenderer).

## Prerequisites
* Java 21 
* Gradle 


## How to Run
1. Clone the Repository:
```commandline
git clone https://github.com/rinshadkv/pdf-generator.git
cd pdf-generator
```

2. Build the Project:

```commandline
./gradlew build
```

3. Run the Application:
```commandline
./gradlew bootRun
```

4. The server will start at:
```commandline
http://localhost:8080
```

## API Endpoints
1. Generate PDF
   *  Endpoint: POST /generate
   * Description: Accepts invoice data and generates a PDF.
   * Request Body (JSON example):
     ```
       {
        "seller": "XYZ Pvt. Ltd.",
        "sellerGstin": "29AABBCCDD121ZD",
        "sellerAddress": "New Delhi, India",
        "buyer": "Vedant Computers",
        "buyerGstin": "29AABBCCDD131ZD",
        "buyerAddress": "Mumbai, India",
        "items": [
        {
        "name": "Product 1",
        "quantity": "12 Nos",
        "rate": 123.00,
        "amount": 1476.00
        }
        ]
        }
    * Response:
   
      201 Created: PDF generated successfully.
2. Download PDF
      * Endpoint: GET /download/{uniqueId}
      * Description: Downloads the previously generated PDF.
      * Response: Returns the PDF file.