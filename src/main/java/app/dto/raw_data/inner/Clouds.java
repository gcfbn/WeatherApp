package app.dto.raw_data.inner;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;

@JsonSerialize
@JsonDeserialize
public record Clouds(int all)
        implements Serializable {
    @Serial
    private static final long serialVersionUID = 5777671661032196281L;
}
