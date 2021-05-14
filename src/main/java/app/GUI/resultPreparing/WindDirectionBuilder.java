package app.GUI.resultPreparing;

public class WindDirectionBuilder {

    public static String buildDirection(int windAngle) {

        String windDirection;

        if ((windAngle >= 330 && windAngle < 360) || (windAngle >= 0 && windAngle < 30)) windDirection = "N";
        else if (windAngle >= 30 && windAngle < 60) windDirection = "NE";
        else if (windAngle >= 60 && windAngle < 120) windDirection = "E";
        else if (windAngle >= 120 && windAngle < 150) windDirection = "SE";
        else if (windAngle >= 150 && windAngle < 210) windDirection = "S";
        else if (windAngle >= 210 && windAngle < 240) windDirection = "SW";
        else if (windAngle >= 240 && windAngle < 300) windDirection = "W";
        else windDirection = "NW";

        return windDirection;
    }
}
