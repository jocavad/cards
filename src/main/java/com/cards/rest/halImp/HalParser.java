package com.cards.rest.halImp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HalParser {

    public HalParser(){
    }
    
    public<T> T createEntityObject(JsonNode jn,Class<T> cls){
        resolve((ObjectNode)jn);
        removeEmbeddedProp((ObjectNode)jn);
        return Utils.getMapper().convertValue(jn, cls);
    }
    
    public Representation createRepObject(JsonNode jn){
        resolve((ObjectNode)jn);
        
        Representation rep = new Representation.RepBuilder(null, null).build();
        Iterator<Map.Entry<String, JsonNode>> it=jn.fields();
        while(it.hasNext()){
            Map.Entry<String,JsonNode> jn2 = it.next();
            if(!jn2.getKey().equals("_embedded")&&!jn2.getKey().equals("_links")){
                rep.getProp().put(jn2.getKey(), jn2.getValue());
            }
            if(jn2.getKey().equals("_embedded")){
                Iterator<Map.Entry<String, JsonNode>> it2=jn2.getValue().fields();
                while(it2.hasNext()){
                    Map.Entry<String,JsonNode> jn3 = it2.next();
                    if(jn3.getValue().isArray()){
                        ArrayNode arrayNode = (ArrayNode) jn3.getValue();
                        List<Representation> ls = new ArrayList<>();
                        for(JsonNode j:arrayNode){
                            ls.add(createRepObject(j));
                        }
                        rep.getEmbedded().put(jn3.getKey(), ls);
                    } else
                      rep.getEmbedded().put(jn3.getKey(), createRepObject(jn3.getValue()));
                }
            }
            if(jn2.getKey().equals("_links")){
                Iterator<Map.Entry<String, JsonNode>> it2=jn2.getValue().fields();
                    while(it2.hasNext()){
                        Map.Entry<String,JsonNode> jn3 = it2.next();
                        if(jn3.getKey().equals("curies")){
                            ArrayNode arrayNode = (ArrayNode) jn3.getValue();
                            List<Link> ls = new ArrayList<>();
                            rep.getLinks().put(jn3.getKey(), ls);
                            for(JsonNode j:arrayNode){
                                ls.add(Utils.getMapper().convertValue(j, Link.class));
                            }
                        }else{
                            rep.getLinks().put(jn3.getKey(),Utils.getMapper().convertValue(jn3.getValue(), Link.class) );
                        }
                    }
            }
        }
        return rep;
    }
    
    private void removeEmbeddedProp(ObjectNode on){
    
        if(!on.path("_embedded").isMissingNode()){
            JsonNode jn = on.path("_embedded");
            on.remove("_embedded");
            Iterator<Map.Entry<String, JsonNode>> it=jn.fields();
            while (it.hasNext()) {
                Map.Entry<String,JsonNode> jn2 = it.next();
                    if(jn2.getValue().isArray()){
                        ArrayNode arrayNode = (ArrayNode) jn2.getValue();
                        for(JsonNode j:arrayNode){
                            removeEmbeddedProp((ObjectNode)j);
                        }
                    }else{
                        removeEmbeddedProp((ObjectNode)jn2.getValue());
                    }
            }
            Iterator<Map.Entry<String, JsonNode>> it2=jn.fields();
            while (it2.hasNext()) {
                Map.Entry<String,JsonNode> jn3 = it2.next();
                on.set(jn3.getKey(), jn3.getValue());
            }
        }
    }

    private void resolve(ObjectNode on){
        
            Map<String, Link> cmap = new HashMap<>();
            process(on, cmap);
    }

    private void process(ObjectNode on, Map<String, Link> cmap){
    
        if(!on.path("_links").path("curies").isMissingNode()){
            cmapLinks(on.path("_links").path("curies"), cmap);
        }
        propsResolve(on, cmap);
        if(!on.path("_embedded").isMissingNode()){
            EmbCurTraverse((ObjectNode)on.path("_embedded"), cmap);
        }
    }

    private String resolveHref(String key, String href){
        return href.contains("{")?href.substring(0, href.indexOf("{"))+key+href.substring(href.indexOf("}")+1):href;
    }

    private void propsResolve(ObjectNode on,  Map<String, Link> cmap){
    
        Map<String, JsonNode> tmp = new HashMap<>();
        JsonNode jn = on;
        Iterator<Map.Entry<String, JsonNode>> it=jn.fields();
        while (it.hasNext()) {
            Map.Entry<String,JsonNode> jn3 = it.next();
            if(!jn3.getKey().equals("_links") && !jn3.getKey().equals("_embedded")){
             if(jn3.getKey().contains(":")){
               String keyProp = jn3.getKey().substring(jn3.getKey().indexOf(":")+1);
               String keyCmap = jn3.getKey().substring(0, jn3.getKey().indexOf(":"));
               if(cmap.containsKey(keyCmap)){
                     tmp.put(resolveHref(keyProp, cmap.get(keyCmap).getHref()), jn3.getValue());
                     it.remove();
               }
             }
            }
        }
        tmp.forEach((k, v)->{
             on.set(k, v);
        });
    }

    private void cmapLinks(JsonNode lnk, Map<String, Link> cmap){

        ArrayNode arrayNode = (ArrayNode) lnk;
             for(JsonNode j:arrayNode){
                    Link l = Utils.getMapper().convertValue(j, Link.class);
                      if(!cmap.containsValue(l)){
                        cmap.put(j.get("name").asText(), l);
                      }
                    }
    }
    
    private void EmbCurTraverse(ObjectNode on, Map<String, Link> cmap){

        JsonNode jn = on;
        Map<String, JsonNode> tmp = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> it=jn.fields();
        while (it.hasNext()) {
            Map.Entry<String,JsonNode> jn2 = it.next();
                if(jn2.getValue().isArray()){
                    ArrayNode arrayNode = (ArrayNode) jn2.getValue();
                    for(JsonNode j:arrayNode){
                            Map<String, Link> cmape = new HashMap<>();
                            cmape.putAll(cmap);
                            process((ObjectNode)j, cmape);
                    }
                }else{
                    Map<String, Link> cmape = new HashMap<>();
                    cmape.putAll(cmap);
                    process((ObjectNode)jn2.getValue(), cmape);
                }
           String keyProp;
           String keyCmap;
           if(jn2.getKey().contains(":")){
              keyProp = jn2.getKey().substring(jn2.getKey().indexOf(":")+1);
              keyCmap = jn2.getKey().substring(0, jn2.getKey().indexOf(":"));
                if(cmap.containsKey(keyCmap)){
                    tmp.put(resolveHref(keyProp, cmap.get(keyCmap).getHref()), jn2.getValue());
                    it.remove();
                }
           }
        }
        tmp.forEach((k, v)->{
             on.set(k, v);
        });
    }
   
}
