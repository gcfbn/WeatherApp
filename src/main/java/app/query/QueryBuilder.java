package app.query;

import app.language.Language;

public class QueryBuilder {

    public static Query buildQuery(String cityName, Units units, Language language) {
        return new Query(HexSpaceConverter.spacesToHex(cityName), units, language);
    }
}
