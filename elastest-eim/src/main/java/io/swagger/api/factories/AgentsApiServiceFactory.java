package io.swagger.api.factories;

import io.swagger.api.AgentsApiService;
import io.swagger.api.impl.AgentsApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-06-23T09:46:23.390Z")
public class AgentsApiServiceFactory {
    private final static AgentsApiService service = new AgentsApiServiceImpl();

    public static AgentsApiService getAgentsApi() {
        return service;
    }
}
