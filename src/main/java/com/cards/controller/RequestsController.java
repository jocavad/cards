package com.cards.controller;

import com.cards.entity.Requests;
import com.cards.service.RequestsService;
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
@SessionAttributes({"reqPage"})
public class RequestsController {
    
    private final RequestsService reqServ;

    public RequestsController(@Qualifier("REQ_SERV") RequestsService reqServ) {
        this.reqServ = reqServ;
    }
    
    @RequestMapping(value = "/requests",method = RequestMethod.GET)
    public String welcome(){
        return "redirect:requests/list";
    }
    
     
    @RequestMapping(value = "requests/list", method = {RequestMethod.GET,RequestMethod.POST})
    public String listRequests(@RequestParam(name = "reqPage",required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "ltype",required = false, defaultValue = "1") Integer lType,
                              ModelMap model){
//pagination
// 5 records per page. Get one record extra. If size is less than 6 then this is the last page, if size is 6 then there are more pages.        
        List<Requests> ls = reqServ.getRange((5*page)-5, 6);
        model.addAttribute("reqLastPage", ls.size()<6 ? 1 : 0);
        model.addAttribute("reqList", ls);
        model.addAttribute("reqPage", page);
        model.addAttribute("ltype", lType);// list(1) or popup(!1)
        if(lType.equals(1))
            return "requests/requests_list";
        else
            return "requests/requests_popup";
    }
  
    @RequestMapping(value = "requests/record/{id:[\\d]+|new}", method = RequestMethod.GET)
    public String RequestsRecord(@PathVariable(value = "id")Object id, ModelMap model){
        if(id.toString().equals("new")){
            model.addAttribute("ReqRec",new Requests());
            return "requests/requests_record";
        }
            model.addAttribute("ReqRec",reqServ.get(Integer.parseInt(id.toString())));
            return "requests/requests_record";
    }
//  add record
    @RequestMapping(value = "requests/record/modify", method = RequestMethod.POST, params = "crt")
    public String addRequestsRecord(@Valid @ModelAttribute("ReqRec") Requests req, BindingResult br,
                                   ModelMap model, SessionStatus status){
        if(br.hasErrors()){
            return "requests/requests_record";
        }
        reqServ.add(req);
        status.setComplete();
        return "redirect:/requests";
    }
    
//  edit/remove record
    @RequestMapping(value = "requests/record/modify", method = RequestMethod.POST)
    public String editRequestsRecord(@Valid @ModelAttribute("ReqRec") Requests req, BindingResult br,
                                    ModelMap model, HttpServletRequest request, SessionStatus status){
        if(br.hasErrors()){
            return "requests/requests_record";
        }
        if(req.getRequestId() != null && request.getParameter("mod")!=null){
            reqServ.modify(req);
            status.setComplete();
            return "redirect:/requests/list";
        }
        else if(req.getRequestId() != null && request.getParameter("del")!=null){
            reqServ.remove(req.getRequestId());
            status.setComplete();
            return "redirect:/requests";
        }
        return "redirect:/requests/list";
    }

}
