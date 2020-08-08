package com.cards.rest.halImp;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;


public class RepSerializer extends StdSerializer<Representation> {

    public RepSerializer() { 
        this(null); 
    } 
 
    public RepSerializer(Class<Representation> vc) { 
        super(vc);
    }
    
    @Override
    public void serialize(Representation representation, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ObjectNode node = Utils.getMapper().valueToTree(representation.getProp());
        if (!representation.getLinks().isEmpty()) {
            node.set("_links", Utils.getMapper().valueToTree(representation.getLinks()));
        }
        if (!representation.getEmbedded().isEmpty()) {
            node.set("_embedded", Utils.getMapper().valueToTree(representation.getEmbedded()));
        }
        jsonGenerator.useDefaultPrettyPrinter();
        jsonGenerator.writeObject(node);
    }    
}
