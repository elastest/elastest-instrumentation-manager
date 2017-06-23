package io.swagger.api.factories;

import io.swagger.api.AgentApiService;
import io.swagger.api.impl.AgentApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-06-23T09:46:23.390Z")
public class AgentApiServiceFactory {
    private final static AgentApiService service = new AgentApiServiceImpl();

    public static AgentApiService getAgentApi() {
        return service;
    }
}
