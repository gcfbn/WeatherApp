package app.dto.raw_data.inner;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;

@JsonSerialize
@JsonDeserialize
public record Wind(
    int deg,
    double speed
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 7505119672319364768L;
}
