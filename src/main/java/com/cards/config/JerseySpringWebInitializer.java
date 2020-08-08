package com.cards.config;

import com.cards.rest.JerseyAppConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Order(1)
public class JerseySpringWebInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {

    AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
    springContext.register(WebConfig.class);
    servletContext.addListener(new ContextLoaderListener(springContext));
    springContext.setServletContext(servletContext);
    servletContext.setInitParameter("contextConfigLocation", "");

    ServletRegistration.Dynamic servlet1 = servletContext.addServlet("dispatcher", new DispatcherServlet(springContext));
    servlet1.setLoadOnStartup(1);
    servlet1.addMapping("");
        
    ServletRegistration.Dynamic servlet2 = servletContext.addServlet("webapp", new DispatcherServlet(springContext));
    servlet2.setLoadOnStartup(2);
    servlet2.addMapping("/webapp/*");
    
    ServletRegistration.Dynamic servlet3 = servletContext.addServlet("WebService", new ServletContainer());
    servlet3.setLoadOnStartup(3);
    servlet3.setInitParameter("javax.ws.rs.Application", JerseyAppConfig.class.getName());
    servlet3.addMapping("/rest/*");
  }
  
}
