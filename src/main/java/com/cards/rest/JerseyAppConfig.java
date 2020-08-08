package com.cards.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyAppConfig extends ResourceConfig {
    public JerseyAppConfig() {
        packages("com.cards.rest.resource")
       .register(JacksonFeature.class);    
    }    
}
