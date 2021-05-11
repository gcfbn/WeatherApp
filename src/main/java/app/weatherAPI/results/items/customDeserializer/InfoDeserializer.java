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
        // TODO something goes wrong here
        JsonNode jsonNode = p.getCodec().readTree(p);
        JsonNode firstArrayElement = jsonNode.get(0);
        return firstArrayElement.traverse().readValueAs(Info.class);
    }
}
