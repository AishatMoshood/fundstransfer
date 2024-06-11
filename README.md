Funds Transfer Application
Overview
This is a Spring Boot application with a modular structure, using MySQL as the preferred database. The application simulates a money transfer operation between two bank accounts, implementing features such as transfer requests, transaction retrieval, and daily summaries.

Structure
The application is organized in a modular structure. The main module for starting the application is located in the funds-transfer-application directory.

Prerequisites
Java 17
Maven 3.6.3 or later
MySQL 8.0 or later
Getting Started
1. Clone the Repository
bash
Copy code
git clone https://github.com/AishatMoshood/fundstransfer.git

2. Configure the Database
Ensure MySQL is running and create a database for the application:

sql
Copy code
3. Set Up Application Properties
Add your Spring DataSource property values in the application.properties file located in the funds-transfer-application/src/main/resources directory.

properties
Copy code
# MySQL database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
4. Build the Application
Use Maven to build the application:

bash
Copy code
mvn clean install
5. Run the Application
Navigate to the funds-transfer-application directory and start the application:

bash
Copy code
cd funds-transfer-application
mvn spring-boot:run
The application will start on the default port 8080.

Usage
REST Endpoints
Transfer Request:

Endpoint: /fundstransfer/v1/direct/payment
Method: POST
Description: Initiates a transfer between accounts.
Retrieve Transactions:

Endpoint: /fundstransfer/v1/direct/transactions
Method: GET
Description: Retrieves a list of transactions with optional filters like status, accountId, and date range.
Daily Summary:

Endpoint: /fundstransfer/v1/direct/dailysummary
Method: GET
Description: Retrieves the summary of transactions for a specified day.
Scheduled Operations
Transaction Analysis: A scheduled job that runs daily to analyze each transaction and update the successful ones as commission-worthy with the corresponding commission value.
Summary Generation: A scheduled job that runs daily to produce the summary of the transactions for the specified day.
Development
