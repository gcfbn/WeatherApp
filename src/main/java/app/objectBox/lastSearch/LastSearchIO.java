package app.objectBox.lastSearch;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

import java.util.List;
import java.util.Optional;

public class LastSearchIO {

    private BoxStore store;
    private Box<LastSearch> box;
    private Query<LastSearch> emptyQuery;

    public LastSearchIO(String name) {
        store = MyObjectBox.builder().name(name).build();
        box = store.boxFor(LastSearch.class);
        emptyQuery = box.query().build();
    }

    public Optional<LastSearch> readLast() {
        List<LastSearch> records = emptyQuery.find();
        return (records.isEmpty()) ? Optional.empty() : Optional.of(records.get(0));
    }

    public void writeLast(LastSearch lastSearch) {
        emptyQuery.remove();
        box.put(lastSearch);
    }
}
