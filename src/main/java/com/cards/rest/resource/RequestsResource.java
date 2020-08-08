package com.cards.rest.resource;

import com.cards.dto.RequestsDTO;
import com.cards.dto.RequestsEmbeddedDTO;
import com.cards.dto.mapint;
import com.cards.entity.Requests;
import com.cards.rest.halImp.Representation;
import com.cards.service.RequestsService;
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
@Path("reqres")
@Singleton
public class RequestsResource {
    
    private mapint map;
    
    private RequestsService reqServ;

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
    @Qualifier("REQ_SERV")
    public void setReqServ(RequestsService reqServ) {
        this.reqServ = reqServ;
    }
    
    @GET
    @Produces({"application/hal+json"})
    public Response readAll(@DefaultValue("1")
                            @QueryParam("p") Integer page) throws IOException {
        
        Integer pageSize = 5;
        Integer selRecPerPage = pageSize+1;
        List<Requests> ls = reqServ.getRange((pageSize*page)-pageSize, selRecPerPage);
        List<Representation.RepBuilder> lsrb = new ArrayList<>();
        ls.forEach(val->{
                        if(lsrb.size()<pageSize)
                            lsrb.add(new Representation.RepBuilder(map.RequestsToRequestsEmbeddedDTO(val, new RequestsEmbeddedDTO())
                                                                , uriInfo.getBaseUriBuilder()
                                                                         .path(RequestsResource.class)
                                                                         .path(RequestsResource.class, "read")
                                                                         .build(val.getRequestId()).toString()
                                                                  )
                                    );
                        }
                   );
        Representation rep = new Representation.RepBuilder(null, uriInfo.getBaseUriBuilder()
                                                                        .path(RequestsResource.class)
                                                                        .build().toString()
                                                           )
                             .addLink("next", ls.size()<selRecPerPage ? null : uriInfo.getBaseUriBuilder()
                                                                                      .path(RequestsResource.class)
                                                                                      .queryParam("p", page+1)
                                                                                      .build().toString())
                             .addLink("prev", page>1 ? uriInfo.getBaseUriBuilder()
                                                              .path(RequestsResource.class)
                                                              .queryParam("p", page-1)
                                                              .build().toString() 
                                                     : null)
                             .addEmbedded("requests", lsrb)
                             .build()
                ;
        return Response.accepted(rep).build();
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/hal+json"})
    public Response read(@PathParam("id") Integer id,@Context HttpServletRequest req) throws IOException {
        RequestsDTO reqdto = map.RequestsToRequestsDTO(reqServ.get(id), new RequestsDTO());

        List<Representation.RepBuilder> lsb = new ArrayList<>();
        reqdto.getBankApproval().forEach(
                val->{       
                             lsb.add(new Representation.RepBuilder(val, uriInfo.getBaseUriBuilder()
                                                                       .path(BankApprovalResource.class)
                                                                       .path(BankApprovalResource.class, "read")
                                                                       .build(val.getApprovalId())
                                                                       .toString()
                                                                  )
                                    );
                     }
        );
        lsb.sort((Representation.RepBuilder o1, Representation.RepBuilder o2) -> ((Integer)o1.getProp().get("approvalId")).compareTo((Integer)o2.getProp().get("approvalId")));
        
        List<Representation.RepBuilder> lsc = new ArrayList<>();
        reqdto.getCards().forEach(
                val->{       
                             lsc.add(new Representation.RepBuilder(val, uriInfo.getBaseUriBuilder()
                                                                       .path(CardsResource.class)
                                                                       .path(CardsResource.class, "read")
                                                                       .build(val.getCardId())
                                                                       .toString()
                                                                  )
                                    );
                     }
        );
        lsc.sort((Representation.RepBuilder o1, Representation.RepBuilder o2) -> ((Integer)o1.getProp().get("cardId")).compareTo((Integer)o2.getProp().get("cardlId")));
        
        Representation rep = new Representation.RepBuilder(reqdto, uriInfo.getBaseUriBuilder()
                                                                 .path(RequestsResource.class)
                                                                 .path(RequestsResource.class, "read")
                                                                 .build(reqdto.getRequestId()).toString()
                                                           )
                            .addEmbedded("clients", new Representation.RepBuilder(reqdto.getClients(), uriInfo.getBaseUriBuilder()
                                                                                                        .path(ClientsResource.class)
                                                                                                        .path(ClientsResource.class, "read")
                                                                                                        .build(reqdto.getClients().getClientId())
                                                                                                        .toString())
                                        )
                            .addEmbedded("employees", new Representation.RepBuilder(reqdto.getEmployees(), uriInfo.getBaseUriBuilder()
                                                                                                        .path(EmployeesResource.class)
                                                                                                        .path(EmployeesResource.class, "read")
                                                                                                        .build(reqdto.getEmployees().getEmployeeId())
                                                                                                        .toString())
                                        )
                            .addEmbedded("bankApproval", lsb)
                            .addEmbedded("cards", lsc)
                            .build()
                            ;

        return Response.accepted(rep).build();
    }
    
    
    @POST
    @Consumes({"application/hal+json"})
    public Response create(RequestsDTO reqdto){
        Requests req = map.RequestsDTOToRequests(reqdto, new Requests());
        reqServ.add(req);
        return Response.status(Response.Status.CREATED)
                .entity("record created.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(RequestsResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }

    @PUT
    @Consumes({"application/hal+json"})
    public Response update(RequestsDTO reqdto){
        Requests req = map.RequestsDTOToRequests(reqdto, new Requests());
        reqServ.modify(req);
        return Response.status(Response.Status.CREATED)
                .entity("record updated.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(RequestsResource.class)
                             .build()
                    ,"parent")
                .link(uriInfo.getBaseUriBuilder()
                             .path(RequestsResource.class)
                             .path(RequestsResource.class, "read")
                             .build(reqdto.getRequestId())
                    ,"self")
                .build()
                ;
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id){
        reqServ.remove(id);
        return Response.status(Response.Status.CREATED)
                .entity("record deleted.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(RequestsResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }
    
}
