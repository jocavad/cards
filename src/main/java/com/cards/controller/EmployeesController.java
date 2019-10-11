package com.cards.controller;

import com.cards.entity.Employees;
import com.cards.service.EmployeesService;
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
@SessionAttributes({"empPage"})
public class EmployeesController {

    private final EmployeesService empServ;

    public EmployeesController(@Qualifier("EMP_SERV") EmployeesService empServ) {
        this.empServ = empServ;
    }
    
    @RequestMapping(value = "/employees",method = RequestMethod.GET)
    public String welcome(){
        return "redirect:employees/list";
    }

    @RequestMapping(value = "employees/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String listEmployees(@RequestParam(name = "empPage",required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "ltype",required = false, defaultValue = "1") Integer lType,
                              ModelMap model){
//pagination
// 5 records per page. Get one record extra. If size is less than 6 then this is the last page, if size is 6 then there are more pages.        
        List<Employees> ls = empServ.getRange((5*page)-5, 6);
        model.addAttribute("empLastPage", ls.size()<6 ? 1 : 0);
        model.addAttribute("empList", ls );
        model.addAttribute("empPage", page);
        model.addAttribute("ltype", lType);// list(1) or popup(!1)
        if(lType.equals(1))
            return "employees/employees_list";
        else
            return "employees/employees_popup";
    }

    @RequestMapping(value = "employees/record/{id:[\\d]+|new}", method = RequestMethod.GET)
    public String EmployeesRecord(@PathVariable(value = "id") Object id,
                                  @RequestParam(name = "empPage",required = false, defaultValue = "1") Integer page,
                                  ModelMap model){
        if(id.toString().equals("new")){
            model.addAttribute("EmpRec",new Employees());
            return "employees/employees_record";
        }
            model.addAttribute("EmpRec",empServ.get(Integer.parseInt(id.toString())));
            return "employees/employees_record";
    }
    
//  add record
    @RequestMapping(value = "employees/record/modify", method = RequestMethod.POST, params = "crt")
    public String addEmployeesRecord(@Valid @ModelAttribute("EmpRec") Employees emp, BindingResult br,
                                     ModelMap model, SessionStatus status){
        if(br.hasErrors()){
            return "employees/employees_record";
        }
        empServ.add(emp);
        status.setComplete();
        return "redirect:/employees";        
    }

//  edit/remove record
    @RequestMapping(value = "employees/record/modify", method = RequestMethod.POST)
    public String editEmployeesRecord(@Valid @ModelAttribute("EmpRec") Employees emp, BindingResult br,
                                      ModelMap model, HttpServletRequest request, SessionStatus status){
        if(br.hasErrors()){
            return "employees/employees_record";
        }

        if(emp.getEmployeeId() != null && request.getParameter("mod")!=null){
            empServ.modify(emp);
            status.setComplete();
            return "redirect:/employees/list";
        }
        else if(emp.getEmployeeId() != null && request.getParameter("del")!=null){
            empServ.remove(emp.getEmployeeId());
            status.setComplete();
            return "redirect:/employees";
        }
        return "redirect:/employees/list";
    }

}
