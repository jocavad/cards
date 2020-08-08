package com.cards.rest.resource;

import com.cards.dto.ClientsDTO;
import com.cards.dto.ClientsEmbeddedDTO;
import com.cards.dto.mapint;
import com.cards.entity.Clients;
import com.cards.rest.halImp.Representation;
import com.cards.service.ClientsService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
@Path("clires")
@Singleton
public class ClientsResource {
    
    private mapint map;
    
    private ClientsService cliServ;

    private UriInfo uriInfo;
    
    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
        
    @Autowired 
    public void setMap(mapint map) {
        this.map = map;
    }
    
    @Autowired
    @Qualifier("CLI_SERV")
    public void setCliServ(ClientsService cliServ) {
        this.cliServ = cliServ;
    }
    
    @GET
    @Produces({"application/hal+json"})
    public Response readAll(@DefaultValue("1")
                            @QueryParam("p") Integer page) throws IOException {
        
        Integer pageSize = 5;
        Integer selRecPerPage = pageSize+1;
        List<Clients> ls = cliServ.getRange((pageSize*page)-pageSize, selRecPerPage);
        List<Representation.RepBuilder> lsrb = new ArrayList<>();
        ls.forEach(val->{
                        if(lsrb.size()<pageSize)
                            lsrb.add(new Representation.RepBuilder(map.ClientsToClientsEmbeddedDTO(val, new ClientsEmbeddedDTO())
                                                                , uriInfo.getBaseUriBuilder()
                                                                         .path(ClientsResource.class)
                                                                         .path(ClientsResource.class, "read")
                                                                         .build(val.getClientId()).toString()
                                                                  )
                                    );
                        }
                   );
        Representation rep = new Representation.RepBuilder(null, uriInfo.getBaseUriBuilder()
                                                                        .path(ClientsResource.class)
                                                                        .build().toString()
                                                           )
                             .addLink("next", ls.size()<selRecPerPage ? null : uriInfo.getBaseUriBuilder()
                                                                                      .path(ClientsResource.class)
                                                                                      .queryParam("p", page+1)
                                                                                      .build().toString())
                             .addLink("prev", page>1 ? uriInfo.getBaseUriBuilder()
                                                              .path(ClientsResource.class)
                                                              .queryParam("p", page-1)
                                                              .build().toString() 
                                                     : null)
                             .addEmbedded("clients", lsrb)
                             .build()
                ;
        return Response.accepted(rep).build();
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/hal+json"})
    public Response read(@PathParam("id") Integer id,@Context HttpServletRequest req) throws IOException {
        ClientsDTO clidto = map.ClientsToClientsDTO(cliServ.get(id), new ClientsDTO());

        List<Representation.RepBuilder> ls = new ArrayList<>();
        clidto.getRequests().forEach(
                val->{       
                             ls.add(new Representation.RepBuilder(val, uriInfo.getBaseUriBuilder()
                                                                       .path(RequestsResource.class)
                                                                       .path(RequestsResource.class, "read")
                                                                       .build(val.getRequestId())
                                                                       .toString()
                                                                  )
                                    );
                     }
        );
        ls.sort((Representation.RepBuilder o1, Representation.RepBuilder o2) -> ((Integer)o1.getProp().get("requestId")).compareTo((Integer)o2.getProp().get("requestId")));
        
        Representation rep = new Representation.RepBuilder(clidto, uriInfo.getBaseUriBuilder()
                                                                 .path(ClientsResource.class)
                                                                 .path(ClientsResource.class, "read")
                                                                 .build(clidto.getClientId()).toString()
                                                           )
                            .addEmbedded("requests", ls)
                            .build()
                            ;

        return Response.accepted(rep).build();
    }
    
    
    @POST
    @Consumes({"application/hal+json"})
    public Response create(ClientsDTO clidto){
        Clients cli = map.ClientsDTOToClients(clidto, new Clients());
        cliServ.add(cli);//no id allowed since it is auto-generated by the sequence
        return Response.status(Response.Status.CREATED)
                .entity("record created.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(ClientsResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }

    @PUT
    @Consumes({"application/hal+json"})
    public Response update(ClientsDTO clidto){
        Clients cli = map.ClientsDTOToClients(clidto, new Clients());
        cliServ.modify(cli);
        return Response.status(Response.Status.CREATED)
                .entity("record updated.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(ClientsResource.class)
                             .build()
                    ,"parent")
                .link(uriInfo.getBaseUriBuilder()
                             .path(ClientsResource.class)
                             .path(ClientsResource.class, "read")
                             .build(clidto.getClientId())
                    ,"self")
                .build()
                ;
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id){
        cliServ.remove(id);
        return Response.status(Response.Status.CREATED)
                .entity("record deleted.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(ClientsResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }
    
}
