package app.objectBox.converters;

import app.language.Language;
import io.objectbox.converter.PropertyConverter;

public class LanguageConverter implements PropertyConverter<Language, Integer> {
    @Override
    public Language convertToEntityProperty(Integer integer) {
        return switch (integer) {
            case 0 -> Language.ENGLISH;
            case 1 -> Language.POLISH;
            default -> null;
        };
    }

    @Override
    public Integer convertToDatabaseValue(Language language) {
        return language.getIndex();
    }
}
