package app.objectBox.lastSearch;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class LastSearch {
    
    @Id
    public long id;

    public String city;

    public LastSearch(String city) {
        this.city = city;
    }

    public LastSearch() {
    }
}
