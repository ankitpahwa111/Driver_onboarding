# Uber Driver Onboarding Module

## Overview

You are part of a team, building a ride management application like Uber. You are responsible
for the driver onboarding module. As part of this module you are required to build an
interface that will allow a driver to sign-up as a driver partner. The driver onboarding module
should:
- Allow a driver to sign-up and enter their profile information
- Trigger onboarding processes like document collection, background verification, shipping
of tracking device, etc., 
- Allow a driver to mark when they are ready to take a ride

![UI](src/assets/UI.png)

### Table of contents:

- [Detailed Approach](#detailed-approach)
    - [Flow Diagrams](#flow-diagrams)
- [API Contracts](#api-contracts)
- [DB Schema](#db-schema)
- [Local Development](#local-development)
    - [Testing](#testing)
    - [PR Guidelines](#pr-guidelines)


## Detailed Approach

![Primary-High-level-design]( src/assets/HLD.png)

`Onboarding-Service` consumes events from `Kafka` and stores the data in `H-2 SQL DB`. It also maintains a cache
to serve the traffic. We have a `cache-aside` arrangement here, which means that we will first check the cache for the data
and if it is not present in the cache, then we will fetch it from the DB and store it in the cache for future use.


| Dimension   | Salient Points                                                       |
|-------------|----------------------------------------------------------------------|
| Cost        | + Cheap at scale                                                     |
| Complexity  | ~ Low/Medium complexity                                              |
| Scalability | + Highly scalable and can serve huge amounts of reads at low latency |
| Performance | + High throughput since no complex DB query and locks required       |

### Flow Diagrams

#### Sign up new user

![Consume-events-from-kafka](src/assets/Flow1.jpg)

#### Mark user ready

![Retrieve-Top-n-Scorers](src/assets/Flow3.jpg)

## API Contracts

### POST : /api/user

#### Request
```shell
curl --location 'http://localhost:8080/api/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userType": "DRIVER",
    "name": "test_name",
    "contact": "886054970",
    "password": "test@123",
    "age": 32,
    "address": "test_address",
    "identityType": "DRIVING_LICENCE",
    "identityNumber": "123456",
    "id": 3
}'
```
#### Response

_**Sample Response**_

Status Code :- 200 (OK)
```json
{
  "httpStatus": "CREATED",
  "data": {
    "id": 1,
    "userType": "DRIVER",
    "name": "test_name",
    "contact": "886054970",
    "password": "ceb6c970658f31504a901b89dcd3e461",
    "age": 32,
    "address": "test_address",
    "identityType": "DRIVING_LICENCE",
    "identityNumber": "123456",
    "updatedAt": "2023-12-31"
  },
  "message": "Created"
}


```

### GET : /api/user/{id}

#### Request
```shell
curl --location 'http://localhost:8080/api/user/1' 
```
#### Response

_**Sample Response**_

Status Code :- 200 (OK)
```json
{
  "httpStatus": "OK",
  "data": {
    "id": 1,
    "userType": "DRIVER",
    "name": "test_name",
    "contact": "886054970",
    "password": "ceb6c970658f31504a901b89dcd3e461",
    "age": 32,
    "address": "test_address",
    "identityType": "DRIVING_LICENCE",
    "identityNumber": "123456",
    "updatedAt": "2023-12-31"
  },
  "message": "OK"
}
```

### PUT : /api/account/update/{user_id}

#### Request
```shell
curl --location --request PUT 'http://localhost:8080/api/account/update/1' \
--header 'Content-Type: application/json' \
--data '{
    "isOnline": true
}' 
```
#### Response

_**Sample Response**_

Status Code :- 200 (OK)
```json
{
  "httpStatus": "OK",
  "data": {
    "id": 1,
    "user": {
      "id": 1,
      "userType": "DRIVER",
      "name": "test_name",
      "contact": "886054970",
      "password": "ceb6c970658f31504a901b89dcd3e461",
      "age": 32,
      "address": "test_address",
      "identityType": "DRIVING_LICENCE",
      "identityNumber": "123456",
      "updatedAt": "2024-01-01"
    },
    "isOnline": true,
    "updatedAt": "2024-01-01"
  },
  "message": "OK"
}
```

### PUT : /api/onboarding/update/{userId}

#### Request
```shell
curl --location --request PUT 'http://localhost:8080/api/onboarding/update/1' \
--header 'Content-Type: application/json' \
--data '{
    "documentCollection": "COMPLETED",
    "backgroundCheck": "COMPLETED",
    "trackingDevice": "COMPLETED"
}'
```
#### Response

_**Sample Response**_

Status Code :- 200 (OK)
```json
{
  "httpStatus": "OK",
  "data": {
    "id": 1,
    "user": {
      "id": 1,
      "userType": "DRIVER",
      "name": "test_name",
      "contact": "886054970",
      "password": "ceb6c970658f31504a901b89dcd3e461",
      "age": 32,
      "address": "test_address",
      "identityType": "DRIVING_LICENCE",
      "identityNumber": "123456",
      "updatedAt": "2024-01-01"
    },
    "documentCollection": "COMPLETED",
    "backgroundCheck": "COMPLETED",
    "trackingDevice": "COMPLETED",
    "updatedAt": "2024-01-01"
  },
  "message": "OK"
}
```

Status Code : 4xx/5xx
```json
{
    "httpStatus": "BAD_REQUEST",
    "data": null,
    "message": "User details not found"
}
```

```json
{
    "httpStatus": "INTERNAL_SERVER_ERROR",
    "data": "User with Contact number - 886054970 Already Exists",
    "message": "Internal Server Error"
}
```

```json
{
    "httpStatus": "BAD_REQUEST",
    "data": null,
    "message": "User is not onboarded yet "
}
```

## DB Schema

![db-schema](src/assets/DataBase.png)



## Local Development

1. Clone the repo
2. Install all dependencies ( Make sure java and Kafka are setup already )
3. Run `.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties` from dir where Kafka is installed
4. Run `.\bin\windows\kafka-server-start.bat .\config\server.properties` from dir where Kafka is installed
5. This will start your kafka at `localhost:9092`
6. If you run into any error - recheck your environment variables for JAVA_HOME and clean the logs file of Kafka and retry.
7. Run the file [UberOnboardingApplication.java](src/main/java/com/intuit/uber/onboarding/UberOnboardingApplication.java).
8. This will start the Tomcat server at `localhost:8080`
9. You can access the H2 SQL server at http://localhost:8080/h2-console. Refer [application.properties](src/main/resources/application.properties) for credentials.



### Testing
1. Follow the above steps for Local Development.
2. You can use Postman to test the apis
3. Or you can trigger integration tests to verify.

