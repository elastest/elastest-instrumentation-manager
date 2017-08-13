# DefaultApi

All URIs are relative to *http://elastest.io/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteAgent**](DefaultApi.md#deleteAgent) | **DELETE** /agent/{agentId} | Delete a agent
[**getAgent**](DefaultApi.md#getAgent) | **GET** /agent/{agentId} | Retrieve a agent
[**getAgents**](DefaultApi.md#getAgents) | **GET** /agents | Retrieve all agents
[**postAction**](DefaultApi.md#postAction) | **POST** /agent/{agentId}/{actionId} | Submit an action agent
[**postAgent**](DefaultApi.md#postAgent) | **POST** /agent | Register an agent
[**putAgent**](DefaultApi.md#putAgent) | **PUT** /agent/{agentId} | update a agent


<a name="deleteAgent"></a>
# **deleteAgent**
> deleteAgent(agentId)

Delete a agent

A client delete a agent

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String agentId = "agentId_example"; // String | Id of agent to delete
try {
    apiInstance.deleteAgent(agentId);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#deleteAgent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **agentId** | **String**| Id of agent to delete |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAgent"></a>
# **getAgent**
> getAgent(agentId)

Retrieve a agent

A client retrieves a agent

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String agentId = "agentId_example"; // String | Id of agent to return
try {
    apiInstance.getAgent(agentId);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#getAgent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **agentId** | **String**| Id of agent to return |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getAgents"></a>
# **getAgents**
> getAgents(events)

Retrieve all agents

A client retrieves all agents

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
Agent events = new Agent(); // Agent | Events to be published
try {
    apiInstance.getAgents(events);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#getAgents");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **events** | [**Agent**](Agent.md)| Events to be published |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postAction"></a>
# **postAction**
> postAction(agentId, actionId)

Submit an action agent

A client submit an action to agent

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String agentId = "agentId_example"; // String | Id of agent to return
String actionId = "actionId_example"; // String | Id of action to apply
try {
    apiInstance.postAction(agentId, actionId);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#postAction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **agentId** | **String**| Id of agent to return |
 **actionId** | **String**| Id of action to apply |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postAgent"></a>
# **postAgent**
> postAgent(events)

Register an agent

A client registers an agent

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
Agent events = new Agent(); // Agent | Events to be published
try {
    apiInstance.postAgent(events);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#postAgent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **events** | [**Agent**](Agent.md)| Events to be published |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="putAgent"></a>
# **putAgent**
> putAgent(agentId)

update a agent

A client updates a agent

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String agentId = "agentId_example"; // String | Id of agent to return
try {
    apiInstance.putAgent(agentId);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#putAgent");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **agentId** | **String**| Id of agent to return |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

