package com.cards.rest.halImp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@JsonSerialize(using = RepSerializer.class)
public final class Representation {

    private final Map<String, Object> prop = new LinkedHashMap<>();
    private final Map<String, Object> _links = new TreeMap<>();
    private final Map<String, Object> _embedded = new TreeMap<>();
    
    private Representation(){}
    
    public Representation(RepBuilder rb){
       
        Set<Link> cmap = new HashSet<>();
        cmapLinks(rb.getLinks(), getLinks(), cmap);
        propsResolve(rb.getProp(), getProp(), cmap);

        if(!rb.getEmbedded().isEmpty()){
            EmbCurTraverse(rb.getEmbedded(), getEmbedded(), cmap);    
        }

    }
      
    private String resolveKey(String key, Set<Link> cmap){
        for (Link ent : cmap) {
            if(ent.isCurieMatch(key))
                return ent.getName()+":"+ent.getRelFromHref(key);
        }
        return key;
    }
    
    private void propsResolve(Map<String, Object> rbp, Map<String, Object> targetp, Set<Link> cmap){
        rbp.forEach((String k, Object v)->{
           targetp.put(resolveKey(k, cmap), v);
        });
    }
    
    private void cmapLinks(Map<String, Object> rbl, Map<String, Object> targetl, Set<Link> cmap){
        
     rbl.forEach((k,v)->{
        if(k.equals("curies")){
            List<Link> lst = new ArrayList<>();
            List<Link> ls = (ArrayList<Link>)v;
            ls.forEach(val->{
                        if(!cmap.contains(val)){
                            lst.add(val);
                            cmap.add(val);
                        }
                        });
            if(!lst.isEmpty()){
                  targetl.put("curies", lst);
            }
        }else{
            targetl.put(resolveKey(k, cmap), v);
        }
     });
    }
    
    private void EmbCurTraverse(Map<String, Object> emaprb, Map<String, Object> emap, Set<Link> cmap){
        emaprb.forEach((key,val)->{
                                 if(val instanceof Collection){
                                     List<Representation> obj = new ArrayList<>();
                                     emap.put(resolveKey(key, cmap),obj);
                                     ((Collection<RepBuilder>)val).forEach(v->{
                                                                   Set cmape = new HashSet<>();
                                                                   cmape.addAll(cmap);
                                                                   Representation target = new Representation();
                                                                   dupEmbCurRemove(v, target, cmape);
                                                                   obj.add(target);
                                                                         }
                                                                     );
                                 }
                                 else{
                                    Representation target = new Representation();
                                    Set cmape = new HashSet<>();
                                    cmape.addAll(cmap);
                                    dupEmbCurRemove((RepBuilder)val, target, cmape);
                                    emap.put(resolveKey(key, cmape),target);
                                }
                                  }
                    );
    }
    
    private void dupEmbCurRemove(RepBuilder repb, Representation rep, Set<Link> cmap){
        cmapLinks(repb.getLinks(), rep.getLinks(), cmap);
        propsResolve(repb.getProp(),rep.getProp(), cmap);

        if(!repb.getEmbedded().isEmpty()){
            EmbCurTraverse(repb.getEmbedded(), rep.getEmbedded(), cmap);
        }    
    }
    
    public Map<String, Object> getEmbedded() {
        return _embedded;
    }
    
    public Map<String, Object> getProp() {
        return prop;
    }
    
    public Map<String, Object> getLinks() {
        return _links;
    }
    
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    
public final static class RepBuilder{
      
    private Map<String, Object> prop = new LinkedHashMap<>();
    private final Map<String, Object> _links = new TreeMap<>();
    private final Map<String, Object> _embedded = new TreeMap<>();
    
    public RepBuilder(Object object, String uri){
        if (object!=null){
            addPropFromObj(object);
            getLinks().put("self", new Link.LinkBuilder().setHref(uri).build());
        }else{
            getLinks().put("self", new Link.LinkBuilder().setHref(uri).build());
        }
    }

    public void addPropFromObj(Object object){
        JsonNode jn = Utils.getMapper().valueToTree(object);
            try {
                if (jn.isArray()){//if, by any chance, a collection gets passed.
                    getProp().putAll(Utils.getMapper().treeToValue(Utils.getMapper().readTree("{\"\":"+jn.toString()+"}"), Map.class));
                }else{
                    getProp().putAll(Utils.getMapper().treeToValue(jn, Map.class));
                     }
                }
            catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
        }
    }
    
    public Map<String, Object> getProp() {
        return prop;
    }
    
    public RepBuilder setProp(Map<String, Object> prop) {
        this.prop = prop;
        return this;
    }
    
    public RepBuilder addProp(String rel, Object pr) {
        prop.put(rel, pr);
        return this;
    }
//==============================================================================    
    public RepBuilder addLink(String rel, String uri) {
        _links.put(rel, new Link.LinkBuilder().setHref(uri).build());
        return this;
    }

    public RepBuilder addLink(String rel, Link link) {
        _links.put(rel, link);
        return this;
    }
    
    public Map<String, Object> getLinks() {
        return _links;
    }
    
    public RepBuilder addCurie(String curie, String href) {
        List<Link> lsc;
        Object curiesObject = getLinks().get("curies");
        if (curiesObject instanceof List) {
            lsc = (List<Link>)curiesObject;
        } else {
            lsc = new ArrayList<>();
        }
         lsc.add(new Link.LinkBuilder().setHref(href).setName(curie).build());
        getLinks().put("curies", lsc);
        return this;
    }
//==============================================================================
    
    public RepBuilder addEmbedded(String rel, RepBuilder representation) {
        _embedded.put(rel, representation);
        return this;
    }
    
    public RepBuilder addEmbedded(String rel, Collection<RepBuilder> representation) {
        _embedded.put(rel, representation);
        return this;
    }
    
    public Map<String, Object> getEmbedded() {
        return _embedded;
    }     
//==============================================================================    
    
    public Representation build(){
        return new Representation(this);
    }
}

}