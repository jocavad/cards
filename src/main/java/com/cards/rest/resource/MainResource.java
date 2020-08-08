package com.cards.rest.resource;

import com.cards.rest.halImp.Representation;
import java.io.IOException;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.springframework.stereotype.Controller;

@Controller
@Path("/")
@Singleton
public class MainResource {
    
    private UriInfo uriInfo;
    
    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
    
    @GET
    @Produces({"application/hal+json"})
    public Response readAll() throws IOException {
        
        Representation rep = new Representation.RepBuilder(null, uriInfo.getBaseUriBuilder()
                                                                        .path(MainResource.class)
                                                                        .build().toString()
                                                           )
                             .addLink("Clients", uriInfo.getBaseUriBuilder()
                                                        .path(ClientsResource.class)
                                                        .build().toString())
                             .addLink("Employees", uriInfo.getBaseUriBuilder()
                                                          .path(EmployeesResource.class)
                                                          .build().toString())
                             .addLink("Requests", uriInfo.getBaseUriBuilder()
                                                         .path(RequestsResource.class)
                                                         .build().toString())
                             .addLink("BankApproval", uriInfo.getBaseUriBuilder()
                                                             .path(BankApprovalResource.class)
                                                             .build().toString())
                             .addLink("Cards", uriInfo.getBaseUriBuilder()
                                                      .path(CardsResource.class)
                                                      .build().toString())
                             .build()
                ;
        return Response.accepted(rep).build();
    }
}
