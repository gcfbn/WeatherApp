package app.GUI.errorBuilders;

import app.language.Language;
import app.language.ResourceBundleLoader;

public class ReadingErrorBuilder {

    public static Error buildReadingError(Language l){

        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("readingErrors", l);

        String title = resourceBundleLoader.getString("title");
        String text = resourceBundleLoader.getString("text");

        return new Error(title, text);
    }

}
