package app.dto.raw_data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class RawWeatherDataJsonReader {
    private ObjectMapper objectMapper;

    public RawWeatherDataJsonReader() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());

        // do not throw an exception if json contains fields that do not exist in class
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public RawWeatherData fromJson(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, RawWeatherData.class);
    }
}
