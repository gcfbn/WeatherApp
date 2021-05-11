package app.weatherAPI.results.items.customDeserializer;

import app.weatherAPI.results.items.Info;
import app.weatherAPI.results.items.Temperature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import kong.unirest.JsonObjectMapper;

import java.io.IOException;

public class InfoDeserializer extends JsonDeserializer<Info> {

    @Override
    public Info deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        System.out.println("kajak");
        JsonNode jsonNode = p.getCodec().readTree(p);
        JsonNode firstArrayElement = jsonNode.get(0);
        JsonParser parser = firstArrayElement.traverse();
        parser.setCodec(p.getCodec());
        // TODO something goes wrong here
        // readValueAs tries to read value from 0-indexed array element
        // using this, InfoDeserializer parser
        // however, it should parse it like a normal POJO class using default object mapper
        // try to fix it
        return parser.readValueAs(Info.class);
    }
}
