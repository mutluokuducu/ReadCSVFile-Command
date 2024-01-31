# Java CSV to REST API Application

## Description

This Java console application reads customer data from a CSV file and sends it to a REST API.

## Prerequisites

Before you begin, ensure you have the following installed:
- Java JDK 11 or newer
- Maven 3.6 or newer (for dependency management and running the application)

## Getting Started

Follow these steps to get the application up and running on your local machine:

### Installation

1. Clone the repository to your local machine:

2. Navigate to the project directory:

3. Install the project dependencies using Maven:

### Running the Application

To run the application, execute the following command. It will read the mock data from Data/demo_customer_data.csv without requiring any additional parameters:```
```
mvn clean install

mvn exec:java
```

To run with parameter:
```
mvn exec:java -Dexec.args="/demo_customer_data.csv"
```
### Testing

This project uses JUnit and Mockito for unit testing.
Tests are designed to ensure the accuracy and efficiency of the word counting logic,
including its interaction with external services like the Translator API.

To run the tests, use the following Maven command:
```
mvn test
```