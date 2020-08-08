package com.cards.rest.halImp;

//import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {
    private static final ObjectMapper MAPPER;
	static {
		MAPPER = new ObjectMapper()
//			     .registerModule(new SimpleModule()
//                                              .addSerializer(Representation.class, new RepSerializer())
//                           .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES)// need at least jackson 2.10.0 (Payara Server 5.194) for this option
                           .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                           .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                           .registerModule(new JavaTimeModule())
                        ;
//                Version version = MAPPER.version();
//                System.out.println("version - "+version);
	}
        
    public static ObjectMapper getMapper(){
        return MAPPER;
    }

    public static HalParser getParser(){
        return new HalParser();
    }

}
