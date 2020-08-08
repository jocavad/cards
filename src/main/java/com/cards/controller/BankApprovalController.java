package com.cards.controller;

import com.cards.entity.BankApproval;
import com.cards.service.BankApprovalService;
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
@SessionAttributes({"baPage"})
public class BankApprovalController {

    private final BankApprovalService baServ;

    public BankApprovalController(@Qualifier("BA_SERV") BankApprovalService baServ) {
        this.baServ = baServ;
    }
    
    @RequestMapping(value = "/bankApproval",method = RequestMethod.GET)
    public String welcome(){
        return "redirect:bankApproval/list";
    }
    
     
    @RequestMapping(value = "bankApproval/list", method = {RequestMethod.GET,RequestMethod.POST})
    public String listBankApproval(@RequestParam(name = "baPage",required = false, defaultValue = "1") Integer page,
                                   @RequestParam(name = "ltype",required = false, defaultValue = "1") Integer lType,
                                   ModelMap model){
//pagination      
// 5 records per page. Get one record extra. If size is less than 6 then this is the last page, if size is 6 then there are more pages.
        List<BankApproval> ls = baServ.getRange((5*page)-5, 6);
        model.addAttribute("baLastPage", ls.size()<6 ? 1 : 0);
        model.addAttribute("baList", ls);
        model.addAttribute("baPage", page);
        model.addAttribute("ltype", lType);// list(1) or popup(!1)
        if(lType.equals(1))
            return "bankApproval/bankApproval_list";
        else
            return "bankApproval/bankApproval_popup";
    }
  
    @RequestMapping(value = "bankApproval/record/{id:[\\d]+|new}", method = RequestMethod.GET)
    public String bankApprovalRecord(@PathVariable(value = "id")Object id, ModelMap model){
        if(id.toString().equals("new")){
            model.addAttribute("BaRec",new BankApproval());
            return "bankApproval/bankApproval_record";
        }
            model.addAttribute("BaRec",baServ.get(Integer.parseInt(id.toString())));
            return "bankApproval/bankApproval_record";
    }
    
//  add record
    @RequestMapping(value = "bankApproval/record/modify", method = RequestMethod.POST, params = "crt")
    public String addBankApprovalRecord(@Valid @ModelAttribute("BaRec") BankApproval ba, BindingResult br,
                                        ModelMap model, SessionStatus status){
        if(br.hasErrors()){
            return "bankApproval/bankApproval_record";
        }
        baServ.add(ba);
        status.setComplete();
        return "redirect:/webapp/bankApproval"; 
    }

//  edit/remove record    
    @RequestMapping(value = "bankApproval/record/modify", method = RequestMethod.POST)
    public String editBankApprovalRecord(@Valid @ModelAttribute("BaRec") BankApproval ba, BindingResult br,
                                         ModelMap model, HttpServletRequest request, SessionStatus status
            , @RequestParam(name = "mod",required = false)String mod,@RequestParam(name = "del",required = false)String del){
        if(br.hasErrors() && request.getParameter("del")==null){
            return "bankApproval/bankApproval_record";
        }
        
        if(ba.getApprovalId() != null && request.getParameter("mod")!=null){
            baServ.modify(ba);
            status.setComplete();
            return "redirect:/webapp/bankApproval/list";
        }
        else if(ba.getApprovalId() != null && request.getParameter("del")!=null){
            baServ.remove(ba.getApprovalId());
            status.setComplete();
            return "redirect:/webapp/bankApproval";
        }
        return "redirect:/webapp/bankApproval/list";
    }

}
