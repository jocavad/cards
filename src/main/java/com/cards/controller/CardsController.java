package com.cards.controller;

import com.cards.entity.Cards;
import com.cards.service.CardsService;
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
@SessionAttributes({"crdPage","crdCount"})
public class CardsController {
 
    private final CardsService crdServ;
 
    public CardsController(@Qualifier("CRD_SERV") CardsService crdServ) {
        this.crdServ = crdServ;
    }
    
    @RequestMapping(value = "/cards",method = RequestMethod.GET)
    public String welcome(){
        return "redirect:cards/list";
    }
    
     
    @RequestMapping(value = "cards/list", method = {RequestMethod.GET,RequestMethod.POST})
    public String listCards(@RequestParam(name = "crdPage",required = false, defaultValue = "1") Integer page,
                            @RequestParam(name = "ltype",required = false, defaultValue = "1") Integer lType,
                            ModelMap model){
//pagination
// 5 records per page. Get one record extra. If size is less than 6 then this is the last page, if size is 6 then there are more pages.        
        List<Cards> ls = crdServ.getRange((5*page)-5, 6);
        model.addAttribute("crdLastPage", ls.size()<6 ? 1 : 0);
        model.addAttribute("crdList", ls);
        model.addAttribute("crdPage", page);
        model.addAttribute("ltype", lType);// list(1) or popup(!1)
        if(lType.equals(1))
            return "cards/cards_list";
        else
            return "cards/cards_popup";
    }
  
    @RequestMapping(value = "cards/record/{id:[\\d]+|new}", method = RequestMethod.GET)
    public String cardsRecord(@PathVariable(value = "id")Object id, ModelMap model){
        if(id.toString().equals("new")){
            model.addAttribute("CrdRec",new Cards());
            return "cards/cards_record";
        }
            model.addAttribute("CrdRec",crdServ.get(Integer.parseInt(id.toString())));
            return "cards/cards_record";
    }

//  add record    
    @RequestMapping(value = "cards/record/modify", method = RequestMethod.POST, params = "crt")
    public String addCards(@Valid @ModelAttribute("CrdRec") Cards crd, BindingResult br,
                           ModelMap model, SessionStatus status){
        if(br.hasErrors()){
            return "cards/cards_record";
        }
        crdServ.add(crd);
        status.setComplete();
        return "redirect:/webapp/cards";   
    }

//  edit/remove record
    @RequestMapping(value = "cards/record/modify", method = RequestMethod.POST)
    public String editCardsRecord(@Valid @ModelAttribute("CrdRec") Cards crd, BindingResult br,
                                  ModelMap model, HttpServletRequest request, SessionStatus status){
        if(br.hasErrors() && request.getParameter("del")==null){
            return "cards/cards_record";
        }
        
        if(crd.getCardId() != null && request.getParameter("mod")!=null){
            crdServ.modify(crd);
            status.setComplete();
            return "redirect:/webapp/cards/list";
        }
        else if(crd.getCardId() != null && request.getParameter("del")!=null){
            crdServ.remove(crd.getCardId());
            status.setComplete();
            return "redirect:/webapp/cards";
        }
        
        return "redirect:/webapp/cards/list";
    }

}
