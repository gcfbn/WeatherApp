package app.query;

public enum Language {

    ENGLISH("City:", "Units:", "Metric:", "Imperial", "Language:",
            "English", "Polish", "Search", "Reset", "Last search",
            "Minimal temperature:", "Maximal temperature:",
            "Feels like:", "Atmospheric pressure:", "Humidity:", "Wind:",
            "Speed", "Direction:", "Sky:", "Sunrise:", "Sunset:",
            "Overcast:"),

    POLISH("Miasto:", "Jednostki:", "Metryczne", "Imperialne", "Język:",
            "Angielski", "Polski", "Szukaj", "Reset",
            "Ostatnie wyszukiwanie", "Temperatura minimalna:",
            "Temperatura maksymalna:", "Temperatura odczuwalna:", "Ciśnienie:",
            "Wilgotność:", "Wiatr:", "Prędkość", "Kierunek", "Niebo:",
            "Wschód słońca", "Zachód słońca:", "Zachmurzenie:");

    public final String cityLabel, units, metricUnits, imperialUnits, language, englishLanguage, polishLangauge,
            searchButton, reset, lastSearch, minimalTemperatureLabel, maximalTemperatureLabel, feelsLikeLabel,
            pressureLabel, humidityLabel, wind, windSpeedLabel, windDirectionLabel, sky, sunriseLabel, sunsetLabel,
            overcastLabel;

    Language(String cityLabel, String units, String metricUnits, String imperialUnits, String language,
             String englishLanguage, String polishLangauge, String searchButton, String reset, String lastSearch,
             String minimalTemperatureLabel, String maximalTemperatureLabel, String feelsLikeLabel, String pressureLabel,
             String humidityLabel, String wind, String windSpeedLabel, String windDirectionLabel, String sky,
             String sunriseLabel, String sunsetLabel, String overcastLabel) {
        this.cityLabel = cityLabel;
        this.units = units;
        this.metricUnits = metricUnits;
        this.imperialUnits = imperialUnits;
        this.language = language;
        this.englishLanguage = englishLanguage;
        this.polishLangauge = polishLangauge;
        this.searchButton = searchButton;
        this.reset = reset;
        this.lastSearch = lastSearch;
        this.minimalTemperatureLabel = minimalTemperatureLabel;
        this.maximalTemperatureLabel = maximalTemperatureLabel;
        this.feelsLikeLabel = feelsLikeLabel;
        this.pressureLabel = pressureLabel;
        this.humidityLabel = humidityLabel;
        this.wind = wind;
        this.windSpeedLabel = windSpeedLabel;
        this.windDirectionLabel = windDirectionLabel;
        this.sky = sky;
        this.sunriseLabel = sunriseLabel;
        this.sunsetLabel = sunsetLabel;
        this.overcastLabel = overcastLabel;
    }
}
