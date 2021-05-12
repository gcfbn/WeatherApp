package app.language;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleLoader {

    private final ResourceBundle resourceBundle;

    public ResourceBundleLoader(String bundleName, Language language) {
        Locale locale = new Locale(language.getLanguageCode(), language.getCountryCode());
        this.resourceBundle = ResourceBundle.getBundle(bundleName, locale);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}
