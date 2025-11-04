# SpringAWS-Gamehistory-Microservice

A microservice-based application that manages game history and leaderboard functionality using AWS services. This project demonstrates the implementation of AWS cloud services integration with Spring Boot, including message queuing, publish/subscribe patterns, and cloud configuration.

## Overview

- **Goal**: Demonstrate AWS service integration patterns with Spring Boot in a gaming context
- **Implementation**: Utilizes AWS SQS for message queuing and SNS for publish/subscribe functionality
- **Features**: Game history tracking, leaderboard management, and asynchronous game stats processing using DynamoDB.

## Project Structure

- `config` — AWS service configuration and setup
- `controller` — REST endpoints and DTOs for game history and leaderboard
- `entity` — Domain entities (GameHistory)
- `listener` — SQS message consumers and request handlers
- `mapper` — Object mapping utilities
- `producer` — SNS message producers
- `scheduler` — Scheduled tasks and background processes
- `service` — Business logic implementation

## Technologies

- Java 17
- Spring Boot
- AWS SDK
- DynamoDB
- Amazon Simple Queue Service (SQS)
- Amazon Simple Notification Service (SNS)
- Spring Cloud AWS
- Maven

## AWS Services Used

### Amazon SQS (Simple Queue Service)

- Used for receiving game statistics asynchronously
- Handles message queuing and processing
- Implements reliable message delivery

### Amazon SNS (Simple Notification Service)

- Implements publish/subscribe messaging
- Used for broadcasting game events
- Enables decoupled communication between components

## Requirements

- JDK 17
- Maven
- AWS Account
- AWS CLI configured
- Proper AWS credentials and permissions

## How to Run

1. Configure AWS credentials:

   ```
   aws configure
   ```
2. Build the project:

   ```
   ./mvnw clean install
   ```
3. Run the application:

   ```
   ./mvnw spring-boot:run
   ```
4. Run tests:

   ```
   ./mvnw test
   ```

## Features

- Game history tracking and storage
- Leaderboard management
- Asynchronous game stats processing via SQS
- Event broadcasting through SNS
- Scheduled task execution

## API Endpoints

### Game History

- `POST /api/game-history` - Record new game history
- `GET /api/game-history` - Retrieve game history
- Additional endpoints for history management

### Leaderboard

- `GET /api/leaderboard` - Get current leaderboard
- Additional endpoints for leaderboard operations

## Architecture

### Message Flow

1. Game stats are sent to SQS queue
2. SQSConsumer processes incoming messages
3. Processed data is stored in the database
4. Important events are published to SNS topics
5. Scheduled tasks update leaderboards periodically

## Configuration

The application requires proper AWS configuration in `application.properties`:

```properties
# AWS Configuration
aws.region=your-region
aws.sqs.queue.url=your-sqs-queue-url
aws.sns.topic.arn=your-sns-topic-arn
```
