package app.query;

public enum Units {

    METRIC("metric"),
    IMPERIAL("imperial");

    private final String unitsCode;

    public String getUnitsCode() {
        return unitsCode;
    }

    Units(String unitsCode) {
        this.unitsCode = unitsCode;
    }
}