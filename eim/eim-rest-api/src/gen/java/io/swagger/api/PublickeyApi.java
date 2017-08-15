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


package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.PublickeyApiService;
import io.swagger.api.factories.PublickeyApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Publickey;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/publickey")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the publickey API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-15T11:10:32.030+02:00")
public class PublickeyApi  {
   private final PublickeyApiService delegate;

   public PublickeyApi(@Context ServletConfig servletContext) {
      PublickeyApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("PublickeyApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (PublickeyApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = PublickeyApiServiceFactory.getPublickeyApi();
      }

      this.delegate = delegate;
   }

    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieve public key", notes = "A client retrieves the public key", response = Publickey.class, tags={ "Publickey", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation", response = Publickey.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Publickey.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Publickey.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = Publickey.class) })
    public Response getPublickey(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getPublickey(securityContext);
    }
}
