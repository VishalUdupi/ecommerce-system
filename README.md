E-Commerce System

A microservices-based e-commerce backend built with Java, Spring Boot, and Spring Cloud, featuring dedicated services for authentication, product management, API routing, and service discovery.

Architecture

flowchart TD
    Client[Client] --> Gateway[API Gateway]
    Gateway --> Auth[Auth Service]
    Gateway --> Product[Product Service]
    Auth -.-> Eureka[Eureka Discovery Server]
    Product -.-> Eureka
    Auth --> DB[(PostgreSQL)]
    Product --> DB

Tech Stack

Language: Java 17

Framework: Spring Boot 3.5.x

Microservices: Spring Cloud

API Gateway: Spring Cloud Gateway

Service Discovery: Netflix Eureka

Security: Spring Security, JWT

Persistence: Spring Data JPA, Hibernate

Database: PostgreSQL

Build Tool: Maven

Services

Service

Port

Responsibility

API Gateway

8080

Routes client requests

Authentication Service

8081

Authentication and JWT-related functionality

Discovery Server

8761

Service registration and discovery

Product Service

Configurable

Product-related operations

Project Structure

ecommerce-system/
├── api-gateway/
├── auth-service/
├── discovery-server/
└── product-service/
