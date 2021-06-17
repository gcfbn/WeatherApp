package app.objectBox.responseCache;

import app.objectBox.MyObjectBox;
import app.objectBox.converters.LanguageConverter;
import app.objectBox.converters.UnitsConverter;
import app.query.HexSpaceConverter;
import app.query.Query;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.PropertyQuery;
import io.objectbox.query.QueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseCacheIO {

    private final BoxStore store;
    private final Box<ResponseRecord> box;

    public ResponseCacheIO(String name) {
        store = MyObjectBox.builder().name(name).build();
        box = store.boxFor(ResponseRecord.class);
    }

    public void write(ResponseRecord record) {
        box.put(record);
    }

    public void clear() {

        // build empty query
        io.objectbox.query.Query<ResponseRecord> emptyQuery = box.query().build();

        emptyQuery.remove();
    }

    public List<ResponseRecord> read(Query weatherQuery) {

        // build query
        io.objectbox.query.Query<ResponseRecord> objectBoxQuery = box.query()
                .equal(ResponseRecord_.cityName, weatherQuery.city()).and()
                .equal(ResponseRecord_.units, new UnitsConverter().convertToDatabaseValue(weatherQuery.units())).and()
                .equal(ResponseRecord_.language, new LanguageConverter().convertToDatabaseValue(weatherQuery.language()))
                .order(ResponseRecord_.timestamp, QueryBuilder.DESCENDING)
                .build();

        // skip 0 elements and return at most 1 result
        return objectBoxQuery.find(0, 1);
    }

    public List<String> readCities() {
        // build PropertyQuery returning distinct city names
        PropertyQuery propertyQuery = box.query().build().property(ResponseRecord_.cityName);

        // get cities from objectbox database
        String[] cities = propertyQuery.distinct().findStrings();

        // replace hex space characters with ' ' space characters
        // and return as list
        return Arrays.stream(cities).map(HexSpaceConverter::hexToSpaces).collect(Collectors.toList());
    }
}