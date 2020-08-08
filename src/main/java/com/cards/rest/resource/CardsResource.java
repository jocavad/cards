package com.cards.rest.resource;

import com.cards.dto.CardsDTO;
import com.cards.dto.CardsEmbeddedDTO;
import com.cards.dto.mapint;
import com.cards.entity.Cards;
import com.cards.rest.halImp.Representation;
import com.cards.service.CardsService;
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
@Path("crdres")
@Singleton
public class CardsResource {
    
private mapint map;
    
    private CardsService crdServ;

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
    @Qualifier("CRD_SERV")
    public void setCrdServ(CardsService crdServ) {
        this.crdServ = crdServ;
    }
    
    @GET
    @Produces({"application/hal+json"})
    public Response readAll(@DefaultValue("1")
                            @QueryParam("p") Integer page) throws IOException {
        
        Integer pageSize = 5;
        Integer selRecPerPage = pageSize+1;
        List<Cards> ls = crdServ.getRange((pageSize*page)-pageSize, selRecPerPage);
        List<Representation.RepBuilder> lsrb = new ArrayList<>();
        ls.forEach(val->{
                        if(lsrb.size()<pageSize)
                            lsrb.add(new Representation.RepBuilder(map.CardsToCardsEmbeddedDTO(val, new CardsEmbeddedDTO())
                                                                , uriInfo.getBaseUriBuilder()
                                                                         .path(CardsResource.class)
                                                                         .path(CardsResource.class, "read")
                                                                         .build(val.getCardId()).toString()
                                                                  )
                                    );
                        }
                   );
        Representation rep = new Representation.RepBuilder(null, uriInfo.getBaseUriBuilder()
                                                                        .path(CardsResource.class)
                                                                        .build().toString()
                                                           )
                             .addLink("next", ls.size()<selRecPerPage ? null : uriInfo.getBaseUriBuilder()
                                                                                      .path(CardsResource.class)
                                                                                      .queryParam("p", page+1)
                                                                                      .build().toString())
                             .addLink("prev", page>1 ? uriInfo.getBaseUriBuilder()
                                                              .path(CardsResource.class)
                                                              .queryParam("p", page-1)
                                                              .build().toString() 
                                                     : null)
                             .addEmbedded("cards", lsrb)
                             .build()
                ;
        return Response.accepted(rep).build();
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/hal+json"})
    public Response read(@PathParam("id") Integer id,@Context HttpServletRequest req) throws IOException {
        CardsDTO crddto = map.CardsToCardsDTO(crdServ.get(id), new CardsDTO());

        Representation rep = new Representation.RepBuilder(crddto, uriInfo.getBaseUriBuilder()
                                                                 .path(CardsResource.class)
                                                                 .path(CardsResource.class, "read")
                                                                 .build(crddto.getCardId()).toString()
                                                           )
                            .addEmbedded("requests", new Representation.RepBuilder(crddto.getRequests(), uriInfo.getBaseUriBuilder()
                                                                                                        .path(RequestsResource.class)
                                                                                                        .path(RequestsResource.class, "read")
                                                                                                        .build(crddto.getRequests().getRequestId())
                                                                                                        .toString())
                                        )
                            .build()
                            ;

        return Response.accepted(rep).build();
    }
    
    
    @POST
    @Consumes({"application/hal+json"})
    public Response create(CardsDTO crddto){
        Cards crd = map.CardsDTOToCards(crddto, new Cards());
        crdServ.add(crd);
        return Response.status(Response.Status.CREATED)
                .entity("record created.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(CardsResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }

    @PUT
    @Consumes({"application/hal+json"})
    public Response update(CardsDTO crddto){
        Cards crd = map.CardsDTOToCards(crddto, new Cards());
        crdServ.modify(crd);
        return Response.status(Response.Status.CREATED)
                .entity("record updated.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(CardsResource.class)
                             .build()
                    ,"parent")
                .link(uriInfo.getBaseUriBuilder()
                             .path(CardsResource.class)
                             .path(CardsResource.class, "read")
                             .build(crddto.getCardId())
                    ,"self")
                .build()
                ;
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id){
        crdServ.remove(id);
        return Response.status(Response.Status.CREATED)
                .entity("record deleted.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(CardsResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }
    
}
