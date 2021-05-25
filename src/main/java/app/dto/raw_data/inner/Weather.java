package app.dto.raw_data.inner;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;

@JsonSerialize
@JsonDeserialize
public record Weather(
    String icon,
    String description,
    String main,
    int id
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -2914640342018244745L;
}
