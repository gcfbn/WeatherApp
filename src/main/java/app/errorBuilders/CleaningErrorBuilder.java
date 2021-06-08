package app.errorBuilders;

import app.language.Language;
import app.language.ResourceBundleLoader;

public class CleaningErrorBuilder {

    public static Error buildCleaningError(Language l) {

        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("cleaningErrors", l);

        String title = resourceBundleLoader.getString("title");
        String text = resourceBundleLoader.getString("text");

        return new Error(title, text);
    }
}