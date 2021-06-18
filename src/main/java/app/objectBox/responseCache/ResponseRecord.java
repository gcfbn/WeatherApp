package app.objectBox.responseCache;

import app.language.Language;
import app.objectBox.converters.LanguageConverter;
import app.objectBox.converters.UnitsConverter;
import app.query.Query;
import app.query.Units;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ResponseRecord {

    @Id
    public long id;

    @Convert(converter = LanguageConverter.class, dbType = Integer.class)
    public Language language;

    @Convert(converter = UnitsConverter.class, dbType = Integer.class)
    public Units units;

    public String cityName;
    public String responseFilePath;

    public long timestamp;

    public ResponseRecord(Query weatherQuery, String responseFilePath, long timestamp) {
        this.language = weatherQuery.language();
        this.units = weatherQuery.units();
        this.cityName = weatherQuery.city();
        this.responseFilePath = responseFilePath;
        this.timestamp = timestamp;
    }

    public ResponseRecord(Language l, Units u, String responseFilePath, String cityName, long timestamp) {
        this.language = l;
        this.units = u;
        this.responseFilePath = responseFilePath;
        this.cityName = cityName;
        this.timestamp = timestamp;
    }

    public ResponseRecord() {
    }
}
