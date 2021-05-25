package app.errorBuilders;

import app.language.Language;
import app.language.ResourceBundleLoader;

public class JsonErrorBuilder {

    public static Error buildJsonError(Language l){

        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("jsonErrors", l);

        String title = resourceBundleLoader.getString("title");
        String text = resourceBundleLoader.getString("text");

        return new Error(title, text);
    }
}
