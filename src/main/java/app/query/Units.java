package app.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Units {

    METRIC("metric"),
    IMPERIAL("imperial");

    private final String unitsCode;
}