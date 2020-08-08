package com.cards.rest.resource;

import com.cards.dto.EmployeesDTO;
import com.cards.dto.EmployeesEmbeddedDTO;
import com.cards.dto.mapint;
import com.cards.entity.Employees;
import com.cards.rest.halImp.Representation;
import com.cards.service.EmployeesService;
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
@Path("empres")
@Singleton
public class EmployeesResource {
    
    private mapint map;
    
    private EmployeesService empServ;

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
    @Qualifier("EMP_SERV")
    public void setEmpServ(EmployeesService empServ) {
        this.empServ = empServ;
    }
    
    @GET
    @Produces({"application/hal+json"})
    public Response readAll(@DefaultValue("1")
                            @QueryParam("p") Integer page) throws IOException {
        
        Integer pageSize = 5;
        Integer selRecPerPage = pageSize+1;
        List<Employees> ls = empServ.getRange((pageSize*page)-pageSize, selRecPerPage);
        List<Representation.RepBuilder> lsrb = new ArrayList<>();
        ls.forEach(val->{
                        if(lsrb.size()<pageSize)
                            lsrb.add(new Representation.RepBuilder(map.EmployeesToEmployeesEmbeddedDTO(val, new EmployeesEmbeddedDTO())
                                                                , uriInfo.getBaseUriBuilder()
                                                                         .path(EmployeesResource.class)
                                                                         .path(EmployeesResource.class, "read")
                                                                         .build(val.getEmployeeId()).toString()
                                                                  )
                                    );
                        }
                   );
        Representation rep = new Representation.RepBuilder(null, uriInfo.getBaseUriBuilder()
                                                                        .path(EmployeesResource.class)
                                                                        .build().toString()
                                                           )
                             .addLink("next", ls.size()<selRecPerPage ? null : uriInfo.getBaseUriBuilder()
                                                                                      .path(EmployeesResource.class)
                                                                                      .queryParam("p", page+1)
                                                                                      .build().toString())
                             .addLink("prev", page>1 ? uriInfo.getBaseUriBuilder()
                                                              .path(EmployeesResource.class)
                                                              .queryParam("p", page-1)
                                                              .build().toString() 
                                                     : null)
                             .addEmbedded("employees", lsrb)
                             .build()
                ;
        return Response.accepted(rep).build();
    }
        
    @GET
    @Path("{id}")
    @Produces({"application/hal+json"})
    public Response read(@PathParam("id") Integer id,@Context HttpServletRequest req) throws IOException {
        EmployeesDTO empdto = map.EmployeesToEmployeesDTO(empServ.get(id), new EmployeesDTO());

        List<Representation.RepBuilder> ls = new ArrayList<>();
        empdto.getRequests().forEach(
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
        ls.sort((Representation.RepBuilder o1, Representation.RepBuilder o2) -> ((Integer)o1.getProp().get("requestId")).compareTo((Integer)o2.getProp().get("requestId"))*1);
        
        Representation rep = new Representation.RepBuilder(empdto, uriInfo.getBaseUriBuilder()
                                                                 .path(EmployeesResource.class)
                                                                 .path(EmployeesResource.class, "read")
                                                                 .build(empdto.getEmployeeId()).toString()
                                                           )
                            .addEmbedded("requests", ls)
                            .build()
                            ;

        return Response.accepted(rep).build();
    }
    
    
    @POST
    @Consumes({"application/hal+json"})
    public Response create(EmployeesDTO empdto){
        Employees emp = map.EmployeesDTOToEmployees(empdto, new Employees());
        empServ.add(emp);
        return Response.status(Response.Status.CREATED)
                .entity("record created.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(EmployeesResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }

    @PUT
    @Consumes({"application/hal+json"})
    public Response update(EmployeesDTO empdto){
        Employees emp = map.EmployeesDTOToEmployees(empdto, new Employees());
        empServ.modify(emp);
        return Response.status(Response.Status.CREATED)
                .entity("record updated.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(EmployeesResource.class)
                             .build()
                    ,"parent")
                .link(uriInfo.getBaseUriBuilder()
                             .path(EmployeesResource.class)
                             .path(EmployeesResource.class, "read")
                             .build(empdto.getEmployeeId())
                    ,"self")
                .build()
                ;
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id){
        empServ.remove(id);
        return Response.status(Response.Status.CREATED)
                .entity("record deleted.")
                .link(uriInfo.getBaseUriBuilder()
                             .path(EmployeesResource.class)
                             .build()
                    ,"parent")
                .build()
                ;
    }
    
}
