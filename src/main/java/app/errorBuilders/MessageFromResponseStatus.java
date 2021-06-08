package app.errorBuilders;

import app.language.Language;
import app.language.ResourceBundleLoader;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

public class MessageFromResponseStatus {

    public static String buildStatusMessage(int status, Language l) {

        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("statusErrors", l);

        if (status == 400 || status == 404) {   // invalid request
            return resourceBundleLoader.getString("invalid.city");
        } else if (status == 401 || status == 403) {    // authentication error
            return resourceBundleLoader.getString("authentication.error");
        } else if (status == 500 || status == 501) { // server error
            return resourceBundleLoader.getString("server.error");
        } else if (status == OpenWeatherMapCaller.UNIREST_EXCEPTION) {
            return resourceBundleLoader.getString("unirest.error");
        } else { // status == OpenWeatherMapCaller.JACKSON_EXCEPTION
            return resourceBundleLoader.getString("jackson.error");
        }
    }
}
