package app.objectBox.io;

import app.language.Language;
import app.language.MyObjectBox;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

import java.util.List;
import java.util.Optional;

public class LanguageIO {

    private BoxStore store;
    private Box<Language> box;
    private Query<Language> emptyQuery;

    public LanguageIO(String name){
        store = MyObjectBox.builder().name(name).build();
        box = store.boxFor(Language.class);
        emptyQuery = box.query().build();
    }

    public Optional<Language> readLastLanguage(){
        List<Language> records = emptyQuery.find();
        return (records.isEmpty()) ? Optional.empty() : Optional.of(records.get(0));
    }

    public void writeLastLanguage(Language l){
        emptyQuery.remove();
        box.put(l);
    }
}
