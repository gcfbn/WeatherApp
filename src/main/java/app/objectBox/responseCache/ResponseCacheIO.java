package app.objectBox.responseCache;

import app.objectBox.MyObjectBox;
import app.objectBox.converters.LanguageConverter;
import app.objectBox.converters.UnitsConverter;
import app.query.Query;
import io.objectbox.Box;
import io.objectbox.BoxStore;

import java.util.List;

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

    public List<ResponseRecord> read(Query weatherQuery) {

        // build query
        io.objectbox.query.Query<ResponseRecord> objectBoxQuery = box.query()
                .equal(ResponseRecord_.cityName, weatherQuery.city()).and()
                .equal(ResponseRecord_.units, new UnitsConverter().convertToDatabaseValue(weatherQuery.units())).and()
                .equal(ResponseRecord_.language, new LanguageConverter().convertToDatabaseValue(weatherQuery.language()))
                .build();

        return objectBoxQuery.find();
    }
}