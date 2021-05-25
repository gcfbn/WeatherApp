package app.dto.raw_data.inner;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@JsonSerialize
@JsonDeserialize
public record SysData(
        String country,
        Instant sunrise,
        Instant sunset,
        int id,
        int type
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 5751702282586223447L;
}
