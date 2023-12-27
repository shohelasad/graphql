# GraphQL  Search Query API

## Prerequisites

* Docker 19.03.x (for production level readiness)
* Docker Compose 1.25.x
* Maven 3

## Used Technologies
* Spring Boot 3
* PostgresSQL (for production level readiness)
* Spring Boot Jpa
* Lombok
* Flyway

## Approach
* Create a search query API with  Query { sellers(filter: SellerFilter, page: PageInput,  sortBy: SellerSortBy): SellerPageableResponse! }
* Prepare SellerPageableResponse DTO object for query response with pagination meta information
* Implement Flyway for creating table schema migration and preparing test data
* I usually used H2 database for running test cases without docker in local machine. Here, I add postgresql config in application-test.yml as recommended to test with postgresql
* Implement unit test case and integration test cases with JUnit 
* For search optimization implemented database indexing for seller filter query
* Implemented redis cache for optimization and scaling up the application
* TODO: Implemented load balancer, rate limiter, kafka message queue and database sharding for scaling up

## How to run

### Package the application as a JAR file

```sh
mvn clean install -DskipTests
```

### Run the Spring Boot application and PostgreSQL with Docker Compose

```sh
docker-compose up -d
```

### Run only test cases (as recommended run docker first to test with postgresql)

```sh
mvn test
```


### GraphQL API
http://localhost:8080/graphiql?path=/graphql

Sample Query API:

`{
    sellers(
        filter: {
            searchByName: "Amazon"
            producerIds: ["e7cd8d65-9556-4f94-83db-0d12a1e4c0f0"],
            marketplaceIds: ["amazon.us"]
        }
        page: {
            page: 0
            size: 10
        }
        sortBy: NAME_ASC
        ){
        meta {
            pageNumber
            pageSize
            totalPages
            totalElements
        }
        data {
            sellerName
            externalId
            producerSellerStates {
                producerId
                producerName
                sellerState
                sellerId
            }
            marketplaceId
        }
    }
}`

