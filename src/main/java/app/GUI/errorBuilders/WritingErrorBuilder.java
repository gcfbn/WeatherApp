package app.GUI.errorBuilders;

import app.language.Language;
import app.language.ResourceBundleLoader;

public class WritingErrorBuilder {

    public static Error buildWritingError(Language l){

        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("writingErrors", l);

        String title = resourceBundleLoader.getString("title");
        String text = resourceBundleLoader.getString("text");

        return new Error(title, text);
    }
}