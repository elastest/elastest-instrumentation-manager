# swagger-java-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-java-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/swagger-java-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.DefaultApi;

import java.io.File;
import java.util.*;

public class DefaultApiExample {

    public static void main(String[] args) {
        
        DefaultApi apiInstance = new DefaultApi();
        String agentId = "agentId_example"; // String | Id of agent to delete
        try {
            apiInstance.deleteAgent(agentId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#deleteAgent");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://elastest.io/api*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**deleteAgent**](docs/DefaultApi.md#deleteAgent) | **DELETE** /agent/{agentId} | Delete a agent
*DefaultApi* | [**getAgent**](docs/DefaultApi.md#getAgent) | **GET** /agent/{agentId} | Retrieve a agent
*DefaultApi* | [**getAgents**](docs/DefaultApi.md#getAgents) | **GET** /agents | Retrieve all agents
*DefaultApi* | [**postAction**](docs/DefaultApi.md#postAction) | **POST** /agent/{agentId}/{actionId} | Submit an action agent
*DefaultApi* | [**postAgent**](docs/DefaultApi.md#postAgent) | **POST** /agent | Register an agent
*DefaultApi* | [**putAgent**](docs/DefaultApi.md#putAgent) | **PUT** /agent/{agentId} | update a agent


## Documentation for Models

 - [Agent](docs/Agent.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

david.rojoa@atos.net

