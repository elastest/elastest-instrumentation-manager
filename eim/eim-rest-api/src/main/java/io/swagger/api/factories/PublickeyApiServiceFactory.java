/**
 * Copyright (c) 2017 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *    @author David Rojo Antona (Atos)
 *    
 * Developed in the context of ElasTest EU project http://elastest.io 
 */


package io.swagger.api.factories;

import io.swagger.api.PublickeyApiService;
import io.swagger.api.impl.PublickeyApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-15T11:10:32.030+02:00")
public class PublickeyApiServiceFactory {
    private final static PublickeyApiService service = new PublickeyApiServiceImpl();

    public static PublickeyApiService getPublickeyApi() {
        return service;
    }
}
