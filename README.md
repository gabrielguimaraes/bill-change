# Spring Boot Bill-Change Service

This is a Spring Boot service that exposes a REST API allowing a user to request change for a given bill. The service assumes there are a finite number of coins.

## Description

The application has the following features:

- Available bills: 1, 2, 5, 10, 20, 50, 100
- Available coins: 0.01, 0.05, 0.10, 0.25
- The service starts with 100 coins of each type
- The service utilizes the least amount of coins to make change
- The API validates bad input and responds accordingly
- The service responds with an appropriate message if it does not have enough coins to make change
- The service maintains the state of the coins throughout the transactions

## Total coins available:

- The number of coins is configurable and can be easily adjusted. This can be achieved by modifying the `coins.total` property in the `resources/application.properties` file.

## Requirements

- Java 21
- Gradle 8.5

## How to Use

1. Ensure you have Java 21 installed on your system. You can check your Java version with the following command:

```shell
java -version
```

If you don't have Java21, please download and install it.

2. Clone the repository from GitHub.

3. To run the application, use the Gradle wrapper included with the project by executing the following command in the project directory:

```shell
./gradlew bootRun
```
This command will start the Spring Boot application.

4. After the application has started, you can access the API at the following endpoint:

```shell
localhost:8080/api/change/<value>
```
Replace `<value>` with one of the available bills (1, 2, 5, 10, 20, 50, 100).

5. To get the most number of coins, add the request param "mostAmount=true" to the URL. For example:

```shell
localhost:8080/api/change/10?mostAmount=true
```

## Configuration

To change the total available coins, update the `application.properties` file's `coins.total` property.

## Running tests

Test cases are provided with the code. To run the tests, use the following command in the project directory:

```shell
./gradlew test
```
