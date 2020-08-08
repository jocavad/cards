package com.cards.dto;

import com.cards.entity.BankApproval;
import com.cards.entity.Cards;
import com.cards.entity.Clients;
import com.cards.entity.Employees;
import com.cards.entity.Requests;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.collection.spi.PersistentCollection;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL, 
        imports = LocalDate.class)
public abstract class mapint {
    @Autowired
    private ConversionService cs;
    
    @BeforeMapping
    public <T> Set<T> fixLazyLoadingSet(Collection<?> c, @TargetType Class<?> targetType) {
        if (!Util.wasInitialized(c)) {
            return Collections.emptySet();
        }
        return null;
    }

    @BeforeMapping
    public <T> List<T> fixLazyLoadingList(Collection<?> c, @TargetType Class<?> targetType) {
        if (!Util.wasInitialized(c)) {
            return Collections.emptyList();
        }
        return null;
    }

   static class Util {
       static boolean wasInitialized(Object c) {
           if (!(c instanceof PersistentCollection)) {
               return true;
           }

           PersistentCollection pc = (PersistentCollection) c;
           return pc.wasInitialized();
       }
   }
    
    public BankApproval BankApprovalDTOToBankApproval(BankApprovalDTO source, BankApproval target) {
       target.setApprovalDate(source.getApprovalDate());
       target.setApprovalId(source.getApprovalId());
       target.setRequests(cs.convert(source.getRequests().getRequestId().toString(), Requests.class));
       Set<Cards> sc = new HashSet<>();
       return target;
    }
    
    public Requests RequestsDTOToRequests(RequestsDTO source, Requests target) {
       target.setAccountNumber(source.getAccountNumber());
       target.setRequestDate(source.getRequestDate());
       target.setRequestId(source.getRequestId());
       Set<BankApproval> sb = new HashSet<>();
       source.getBankApproval().forEach((c) -> {
           sb.add(cs.convert(c.getApprovalId().toString(), BankApproval.class));
        });
       target.setBankApproval(sb);
       target.setClients(cs.convert(source.getClients().getClientId().toString(), Clients.class));
       target.setEmployees(cs.convert(source.getEmployees().getEmployeeId().toString(), Employees.class));
       return target;
    }
    
    public Employees EmployeesDTOToEmployees(EmployeesDTO source, Employees target) {
       target.setAddress(source.getAddress());
       target.setEmployeeId(source.getEmployeeId());
       target.setFirstName(source.getFirstName());
       target.setLastName(source.getLastName());
       target.setPhoneNumber(source.getPhoneNumber());
       Set<Requests> sr = new HashSet<>();
       source.getRequests().forEach((c) -> {
           sr.add(cs.convert(c.getRequestId().toString(), Requests.class));
        });
       target.setRequests(sr);
       return target;
    }
    
    public Clients ClientsDTOToClients(ClientsDTO source, Clients target) {
       target.setAddress(source.getAddress());
       target.setClientId(source.getClientId());
       target.setFirstName(source.getFirstName());
       target.setLastName(source.getLastName());
       target.setPhoneNumber(source.getPhoneNumber());
       Set<Requests> sr = new HashSet<>();
       source.getRequests().forEach((c) -> {
           sr.add(cs.convert(c.getRequestId().toString(), Requests.class));
        });
       target.setRequests(sr);
       return target;
    }
    
    public Cards CardsDTOToCards(CardsDTO source, Cards target) {
       target.setCardId(source.getCardId());
       target.setIssueDate(source.getIssueDate());
       target.setPin(source.getPin());
       target.setRequests(cs.convert(source.getRequests().getRequestId().toString(), Requests.class));
       return target;
    }
        
//    @Mapping(source = "approvalDate", target = "approvalDate", qualifiedByName = "ld")
//    BankApproval BankApprovalDTOToBankApproval(BankApprovalDTO source, @MappingTarget BankApproval target);
//    @Mapping(source = "approvalDate", target = "approvalDate", qualifiedByName = "ld")
//    abstract Requests RequestsDTOToRequests(RequestsDTO source, @MappingTarget Requests target);
//    abstract public Employees EmployeesDTOToEmployees(EmployeesDTO source, @MappingTarget Employees target);
//    abstract public Clients ClientsDTOToClients(ClientsDTO source, @MappingTarget Clients target);
//    abstract public Cards CardsDTOToCards(CardsDTO source, @MappingTarget Cards target);
    
    abstract public BankApprovalEmbeddedDTO BankApprovalToBankApprovalEmbeddedDTO(BankApproval source, @MappingTarget BankApprovalEmbeddedDTO target);
    abstract public RequestsEmbeddedDTO RequestsToRequestsEmbeddedDTO(Requests source, @MappingTarget RequestsEmbeddedDTO target);
    abstract public EmployeesEmbeddedDTO EmployeesToEmployeesEmbeddedDTO(Employees source, @MappingTarget EmployeesEmbeddedDTO target);
    abstract public ClientsEmbeddedDTO ClientsToClientsEmbeddedDTO(Clients source, @MappingTarget ClientsEmbeddedDTO target);
    abstract public CardsEmbeddedDTO CardsToCardsEmbeddedDTO(Cards source, @MappingTarget CardsEmbeddedDTO target);
    
    abstract public BankApprovalDTO BankApprovalToBankApprovalDTO(BankApproval source, @MappingTarget BankApprovalDTO target);
    abstract public RequestsDTO RequestsToRequestsDTO(Requests source, @MappingTarget RequestsDTO target);
    abstract public EmployeesDTO EmployeesToEmployeesDTO(Employees source, @MappingTarget EmployeesDTO target);
    abstract public ClientsDTO ClientsToClientsDTO(Clients source, @MappingTarget ClientsDTO target);
    abstract public CardsDTO CardsToCardsDTO(Cards source, @MappingTarget CardsDTO target);
    
    abstract public BankApprovalEmbeddedDTO BankApprovalDTOToBankApprovalEmbeddedDTO(BankApprovalDTO source, @MappingTarget BankApprovalEmbeddedDTO target);
    abstract public RequestsEmbeddedDTO RequestsDTOToRequestsEmbeddedDTO(RequestsDTO source, @MappingTarget RequestsEmbeddedDTO target);
    abstract public EmployeesEmbeddedDTO EmployeesDTOToEmployeesEmbeddedDTO(EmployeesDTO source, @MappingTarget EmployeesEmbeddedDTO target);
    abstract public ClientsEmbeddedDTO ClientsDTOToClientsEmbeddedDTO(ClientsDTO source, @MappingTarget ClientsEmbeddedDTO target);
    abstract public CardsEmbeddedDTO CardsDTOToCardsEmbeddedDTO(CardsDTO source, @MappingTarget CardsEmbeddedDTO target);
    
    abstract public BankApprovalDTO BankApprovalEmbeddedDTOToBankApprovalDTO(BankApprovalEmbeddedDTO source, @MappingTarget BankApprovalDTO target);
    abstract public RequestsDTO RequestsEmbeddedDTOToRequestsDTO(RequestsEmbeddedDTO source, @MappingTarget RequestsDTO target);
    abstract public EmployeesDTO EmployeesEmbeddedDTOToEmployeesDTO(EmployeesEmbeddedDTO source, @MappingTarget EmployeesDTO target);
    abstract public ClientsDTO ClientsEmbeddedDTOToClientsDTO(ClientsEmbeddedDTO source, @MappingTarget ClientsDTO target);
    abstract public CardsDTO CardsEmbeddedDTOToCardsDTO(CardsEmbeddedDTO source, @MappingTarget CardsDTO target);
    
////    @Mapping(source = "ct", target = "childTabs")
//    ParentTab ParentTabDTOToParentTab(ParentTabDTO source);
////    @Mapping(source = "childTabs", target = "ct")
//    ParentTabDTO ParentTabToParentTabDTO(ParentTab destination);
//    
////    @Mapping(source = "ct", target = "childTabs")
//    ChildTab ChildTabDTOToChildTab(ChildTabDTO source);
////    @Mapping(source = "childTabs", target = "ct")
//    ChildTabDTO ChildTabToChildTabDTO(ChildTab destination);
//    
////    @Mapping(source = "ct", target = "childTabs")
//    ParentTab ParentTabPreviewDTOToParentTab(ParentTabPreviewDTO source);
////    @Mapping(source = "childTabs", target = "ct")
//    ParentTabPreviewDTO ParentTabToParentTabPreviewDTO(ParentTab destination);
////    @Mapping(source = "childTabs", target = "ct")    
//    List<ParentTabPreviewDTO> ListParentTabToListParentTabPreviewDTO(List<ParentTab> source);
//
////    @Mapping(source = "ct", target = "childTabs")
//    ChildTabDTO ChildTabPreviewDTOToChildTabDTO(ChildTabPreviewDTO source);
////    @Mapping(source = "childTabs", target = "ct")
//    ChildTabPreviewDTO ChildTabDTOToChildTabPreviewDTO(ChildTabDTO destination);
//    
    
}
