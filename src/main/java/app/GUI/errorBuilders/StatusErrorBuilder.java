package app.GUI.errorBuilders;

import app.language.Language;
import app.language.ResourceBundleLoader;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

public class StatusErrorBuilder {

    public static Error buildErrorText(int status, Language l) {


        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("errors", l);

        String title = resourceBundleLoader.getString("title");

        String text;
        if (status == 400 || status == 404) {   // invalid request
            text = resourceBundleLoader.getString("invalid.city");
        } else if (status == 401 || status == 403) {    // authentication error
            text = resourceBundleLoader.getString("authentication.error");
        } else if (status == 500 || status == 501) { // server error
            text = resourceBundleLoader.getString("server.error");
        } else if (status == OpenWeatherMapCaller.UNIREST_EXCEPTION) {
            text = resourceBundleLoader.getString("unirest.error");
        } else { // status == OpenWeatherMapCaller.JACKSON_EXCEPTION
            text = resourceBundleLoader.getString("jackson.error");
        }

        return new Error(title, text);
    }
}
