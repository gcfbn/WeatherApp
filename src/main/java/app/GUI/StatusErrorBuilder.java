package app.GUI;

import app.language.Language;
import app.language.ResourceBundleLoader;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

public class StatusErrorBuilder {

    public static String buildErrorText(int status, Language l) {

        String error;
        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("errors", l);

        if (status == 400 || status == 404) {   // invalid request
            error = resourceBundleLoader.getString("invalid.city");
        } else if (status == 401 || status == 403) {    // authentication error
            error = resourceBundleLoader.getString("authentication.error");
        } else if (status == 500 || status == 501){ // server error
            error = resourceBundleLoader.getString("server.error");
        } else if (status == OpenWeatherMapCaller.UNIREST_EXCEPTION){
            error = resourceBundleLoader.getString("unirest.error");
        } else { // status == OpenWeatherMapCaller.JACKSON_EXCEPTION
            error = resourceBundleLoader.getString("jackson.error");
        }

        return error;
    }

    public static String buildErrorTitle(Language l) {
        return new ResourceBundleLoader("errors", l).getString("title");
    }
}
