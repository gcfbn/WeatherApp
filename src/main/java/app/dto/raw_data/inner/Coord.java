package app.dto.raw_data.inner;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;

@JsonSerialize
@JsonDeserialize
public record Coord( double lon, double lat )
implements Serializable {
    @Serial
    private static final long serialVersionUID = 1977394477168771643L;
}
