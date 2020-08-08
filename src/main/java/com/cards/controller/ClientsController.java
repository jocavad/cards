package com.cards.controller;

import com.cards.entity.Clients;
import com.cards.service.ClientsService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes({"cliPage"})
public class ClientsController {

    private final ClientsService cliServ;

    public ClientsController(@Qualifier("CLI_SERV") ClientsService cliServ) {
        this.cliServ = cliServ;
    }
    
    @RequestMapping(value = "/clients",method = RequestMethod.GET)
    public String welcome(){
        return "redirect:clients/list";
    }
    
     
    @RequestMapping(value = "clients/list", method = {RequestMethod.GET,RequestMethod.POST})
    public String listClients(@RequestParam(name = "cliPage",required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "ltype",required = false, defaultValue = "1") Integer lType,
                              ModelMap model){
//pagination
// 5 records per page. Get one record extra. If size is less than 6 then this is the last page, if size is 6 then there are more pages.        
        List<Clients> ls = cliServ.getRange((5*page)-5, 6);
        model.addAttribute("cliLastPage", ls.size()<6 ? 1 : 0);
        model.addAttribute("cliList", ls);
        model.addAttribute("cliPage", page);
        model.addAttribute("ltype", lType);// list(1) or popup(!1)
        if(lType.equals(1))
            return "clients/clients_list";
        else
            return "clients/clients_popup";
    }
  
    @RequestMapping(value = "clients/record/{id:[\\d]+|new}", method = RequestMethod.GET)
    public String ClientsRecord(@PathVariable(value = "id")Object id, ModelMap model){
        if(id.toString().equals("new")){
            model.addAttribute("CliRec",new Clients());
            return "clients/clients_record";
        }
            model.addAttribute("CliRec",cliServ.get(Integer.parseInt(id.toString())));
            return "clients/clients_record";
    }

//  add record    
    @RequestMapping(value = "clients/record/modify", method = RequestMethod.POST, params = "crt")
    public String addClientsRecord(@Valid @ModelAttribute("CliRec") Clients cli, BindingResult br,
                                   ModelMap model, SessionStatus status){
        if(br.hasErrors()){
            return "clients/clients_record";
        }
        cliServ.add(cli);
        status.setComplete();
        return "redirect:/webapp/clients";    
    }

//  edit/remove record    
    @RequestMapping(value = "clients/record/modify", method = RequestMethod.POST)
    public String editClientsRecord(@Valid @ModelAttribute("CliRec") Clients cli, BindingResult br,
                                    ModelMap model, HttpServletRequest request, SessionStatus status){
        if(br.hasErrors() && request.getParameter("del")==null){
            return "clients/clients_record";
        }
        
        if(cli.getClientId() != null && request.getParameter("mod")!=null){
            cliServ.modify(cli);
            status.setComplete();
            return "redirect:/webapp/clients/list";
        }
        else if(cli.getClientId() != null && request.getParameter("del")!=null){
            cliServ.remove(cli.getClientId());
            status.setComplete();
            return "redirect:/webapp/clients";
        }
        return "redirect:/webapp/clients/list";
    }

}
