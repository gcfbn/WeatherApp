package app.query;

import app.language.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Query {

    private final String city;
    private final Units units;
    private final Language language;
}