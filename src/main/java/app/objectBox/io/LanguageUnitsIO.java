package app.objectBox.io;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

import java.util.List;
import java.util.Optional;

public class LanguageUnitsIO {

    private BoxStore store;
    private Box<LanguageUnits> box;
    private Query<LanguageUnits> emptyQuery;

    public LanguageUnitsIO(String name){
        store = MyObjectBox.builder().name(name).build();
        box = store.boxFor(LanguageUnits.class);
        emptyQuery = box.query().build();
    }

    public Optional<LanguageUnits> readLast(){
        List<LanguageUnits> records = emptyQuery.find();
        return (records.isEmpty()) ? Optional.empty() : Optional.of(records.get(0));
    }

    public void writeLast(LanguageUnits lu){
        emptyQuery.remove();
        box.put(lu);
    }
}
