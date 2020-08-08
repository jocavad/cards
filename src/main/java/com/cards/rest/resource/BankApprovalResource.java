package com.cards.rest.resource;

import com.cards.dto.BankApprovalDTO;
import com.cards.dto.BankApprovalEmbeddedDTO;
import com.cards.dto.mapint;
import com.cards.entity.BankApproval;
import com.cards.rest.halImp.Representation;
import com.cards.service.BankApprovalService;
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
@Path("bares")
@Singleton
public class BankApprovalResource {

    private mapint map;
    
    private BankApprovalService baServ;

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
    @Qualifier("BA_SERV")
    public void setBaServ(BankApprovalService baServ) {
        this.baServ = baServ;
    }
    
    @GET
    @Produces({"application/hal+json"})
    public Response readAll(@DefaultValue("1")
                            @QueryParam("p") Integer page) throws IOException {
        
        Integer pageSize = 5;
        Integer selRecPerPage = pageSize+1;
        List<BankApproval> ls = baServ.getRange((pageSize*page)-pageSize, selRecPerPage);
        List<Representation.RepBuilder> lsrb = new ArrayList<>();
        ls.forEach(val->{
                        if(lsrb.size()<pageSize)
                            lsrb.add(new Representation.RepBuilder(map.BankApprovalToBankApprovalEmbeddedDTO(val, new BankApprovalEmbeddedDTO())
                                                                , uriInfo.getBaseUriBuilder()
                                                                         .path(BankApprovalResource.class)
                                                                         .path(BankApprovalResource.class, "read")
                                                                         .build(val.getApprovalId()).toString()
                                                                  )
                                    );
                        }
                   );
        Representation rep = new Representation.RepBuilder(null, uriInfo.getBaseUriBuilder()
                                                                        .path(BankApprovalResource.class)
                                                                        .build().toString()
                                                           )
                             .addLink("next", ls.size()<selRecPerPage ? null : uriInfo.getBaseUriBuilder()
                                                                                      .path(BankApprovalResource.class)
                                                                                      .queryParam("p", page+1)
                                                                                      .build().toString())
                             .addLink("prev", page>1 ? uriInfo.getBaseUriBuilder()
                                                              .path(BankApprovalResource.class)
                                                              .queryParam("p", page-1)
                                                              .build().toString() 
                                                     : null)
                             .addEmbedded("bankApprovals", lsrb)
                             .build()
                ;
        return Response.accepted(rep).build();
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/hal+json"})
    public Response read(@PathParam("id") Integer id,@Context HttpServletRequest req) throws IOException {
        BankApprovalDTO badto = map.BankApprovalToBankApprovalDTO(baServ.get(id), new BankApprovalDTO());

        Representation rep = new Representation.RepBuilder(badto, uriInfo.getBaseUriBuilder()
                                                                 .path(BankApprovalResource.class)
                                                                 .path(BankApprovalResource.class, "read")
                                                                 .build(badto.getApprovalId()).toString()
                                                           )
                            .addEmbedded("requests", new Representation.RepBuilder(badto.getRequests(), uriInfo.getBaseUriBuilder()
                                                                                                        .path(RequestsResource.class)
                                                                                                        .path(RequestsResource.class, "read")
                                                                                                        .build(badto.getRequests().getRequestId())
                                                                                                        .toString())
                                        )
                            .build()
                            ;

        return Response.accepted(rep).build();
    }
    
    
    @POST
    @Consumes({"application/hal+json"})
    public Response create(BankApprovalDTO badto){
        BankApproval ba = map.BankApprovalDTOToBankApproval(badto, new BankApproval());
        baServ.add(ba);
        return Response.status(Response.Status.CREATED)
                .entity("record created.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(BankApprovalResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }

    @PUT
    @Consumes({"application/hal+json"})
    public Response update(BankApprovalDTO badto){
        BankApproval ba = map.BankApprovalDTOToBankApproval(badto, new BankApproval());
        baServ.modify(ba);
        return Response.status(Response.Status.CREATED)
                .entity("record updated.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(BankApprovalResource.class)
                             .build()
                    ,"parent")
                .link(uriInfo.getBaseUriBuilder()
                             .path(BankApprovalResource.class)
                             .path(BankApprovalResource.class, "read")
                             .build(badto.getApprovalId())
                    ,"self")
                .build()
                ;
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id){
        baServ.remove(id);
        return Response.status(Response.Status.CREATED)
                .entity("record deleted.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(BankApprovalResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }
    
}
