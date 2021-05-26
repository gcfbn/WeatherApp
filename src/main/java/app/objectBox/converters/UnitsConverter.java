package app.objectBox.converters;

import app.query.Units;
import io.objectbox.converter.PropertyConverter;

public class UnitsConverter implements PropertyConverter<Units, Integer> {

    @Override
    public Units convertToEntityProperty(Integer integer) {
        return switch(integer){
            case 0 -> Units.METRIC;
            case 1 -> Units.IMPERIAL;
            default -> null;
        };
    }

    @Override
    public Integer convertToDatabaseValue(Units units) {
        return units.getIndex();
    }
}
