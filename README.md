# Fetch-rewards

## Overview

Fetch-rewards Receipt Processor is a Java and Spring Boot-based application that allows users to process receipt data and retrieve points associated with purchases.

### APIs:
- **POST /receipts/process**: Saves receipt data from the request body into memory and returns a UUID as the response.
- **GET /receipts/{id}/points**: Fetches the points associated with a specific UUID, which is mapped to the respective purchase.

## Running the Docker Setup

To run the application using Docker, follow these steps:

1. **Build the Docker image**:
   ```bash
   docker build -t receiptprocessor .

2. **Run the docker container
   ```bash
   docker build -t receiptprocessor .

#Testing
##Test cases are written using JUnit and Mockito to ensure the functionality works as expected.


