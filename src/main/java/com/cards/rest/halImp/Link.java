package com.cards.rest.halImp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class Link {
    @JsonIgnore
    String prefix;
    @JsonIgnore
    String sufix;
    @JsonProperty(value = "href")
    private  String href;
    @JsonProperty(value = "templated")
    private Boolean templated;
//    private String type;
//    private URL deprecation;
    @JsonProperty(value = "name")
    private String name;
//    private URL profile;
//    private String title;
//    private String hreflang;

    public Link() {}
    
    public Link(LinkBuilder lb) {
        this.href=lb.getHref();
        this.name=lb.getName();
        this.prefix=lb.getPrefix();
        this.sufix=lb.getSufix();
        this.templated=lb.getTemplated();
    }
    
    public String getName(){
        return name;
    }
        
    public String getHref() {
        return href;
    }
        
    public Boolean getTemplated(){
        return templated;
    }
            
    public String getPrefix(){
        return prefix;
    }
                
    public String getSufix(){
        return sufix;
    }
    
    public String getRelFromHref(String href){
        if(prefix==null && sufix==null){
            return href;
        }else if(href.startsWith(prefix) && href.endsWith(sufix))
            return href.substring(prefix.length(), href.length()-sufix.length());
        else
            return href;
    }
    
    public boolean isCurieMatch(String href){
        if(prefix==null && sufix==null)
            return false;
        else
            return href.startsWith(prefix) && href.endsWith(sufix);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.href);
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        return Objects.equals(this.href, other.href) && Objects.equals(this.name, other.name);
    }
   
    public final static class LinkBuilder{
    private String prefix;
    private String sufix;
    private  String href;
    private Boolean templated;
//    private String type;
//    private URL deprecation;
    private String name;
//    private URL profile;
//    private String title;
//    private String hreflang;
    
    public LinkBuilder(){}
   
    public Boolean getTemplated(){
        return templated;
    }
    
    public LinkBuilder setTemplated(Boolean templated){
        this.templated = templated;
        return this;
    }    
    
    public String getPrefix(){
        return prefix;
    }
    
    public LinkBuilder setPrefix(String prefix){
        this.prefix=prefix;
        return this;
    }
    
    public String getSufix(){
        return sufix;
    }
    
    public LinkBuilder setSufix(String sufix){
        this.sufix=sufix;
        return this;
    }
    
    public String getHref() {
        return href;
    }
    
    public LinkBuilder setHref(String href) {
        this.href = href;
        if (href!=null && href.contains("{")) {
            this.prefix = href.substring(0, href.indexOf("{"));
            this.sufix = href.substring(href.indexOf("}")+1, href.length());
            templated = true;
        } else if (this.templated != null && this.templated) {
            this.templated = null;
        }
        return this;
    }
    
    public String getName(){
        return name;
    }
    
    public LinkBuilder setName(String name){
        this.name=name;
        return this;
    }
    
    public Link build(){
        return new Link(this);
    }
    
    }
    
    
}
