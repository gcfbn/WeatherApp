package app.objectBox.languageUnits;

import app.language.Language;
import app.objectBox.converters.LanguageConverter;
import app.objectBox.converters.UnitsConverter;
import app.query.Units;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class LanguageUnits {

    @Id
    public long id;

    @Convert(converter = LanguageConverter.class, dbType = Integer.class)
    public Language language;

    @Convert(converter = UnitsConverter.class, dbType = Integer.class)
    public Units units;

    public LanguageUnits(Language language, Units units) {
        this.language = language;
        this.units = units;
    }

    public LanguageUnits() {
    }
}
