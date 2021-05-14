package app.weatherAPI.results.items.customDeserializer;

import app.weatherAPI.results.items.Info;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class InfoDeserializer extends JsonDeserializer<Info> {

    @Override
    public Info deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        // get json node containing array
        JsonNode jsonNode = p.getCodec().readTree(p);

        // get first element of the array (it contains icon and description data)
        JsonNode firstArrayElement = jsonNode.get(0);

        // get icon and description from json
        String icon = firstArrayElement.get("icon").asText();
        String description = firstArrayElement.get("description").asText();

        // create object containing this data
        Info info = new Info();
        info.setIcon(icon);
        info.setDescription(description);

        return info;
    }
}
