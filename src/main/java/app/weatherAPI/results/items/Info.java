package app.weatherAPI.results.items;

import app.weatherAPI.results.items.customDeserializer.InfoDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize(using = InfoDeserializer.class)
public class Info {

    private String icon, description;
}
