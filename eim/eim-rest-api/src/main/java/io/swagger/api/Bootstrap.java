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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.elastest.eim.config.Properties;
import io.elastest.eim.database.mysql.EimDbCreator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Swagger;
 
public class Bootstrap extends HttpServlet {
  @Override
  public void init(ServletConfig config) throws ServletException {
    Info info = new Info()
      .title("Swagger Server")
      .description("RESTful API specification for the ElasTest Instrumentation Manager (EIM)")
      .termsOfService("TBD")
      .contact(new Contact()
        .email("david.rojoa@atos.net"))
      .license(new License()
        .name("Apache 2.0")
        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

    ServletContext context = config.getServletContext();
    Swagger swagger = new Swagger().info(info);
    
    // load properties
    String propertiesFile = "/WEB-INF/bootstrap.properties";
    Properties.load(config.getServletContext().getResourceAsStream(propertiesFile), propertiesFile);
    
    // create database schema
    EimDbCreator dbCreator = new EimDbCreator();
    dbCreator.createSchema();
    
    new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
  }
}
