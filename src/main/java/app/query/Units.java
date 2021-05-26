package app.query;

public enum Units {

    METRIC("metric", 0),
    IMPERIAL("imperial", 1);

    private final String unitsCode;
    private final int index;

    public String getUnitsCode() {
        return unitsCode;
    }
    public int getIndex(){ return index; }

    Units(String unitsCode, int index) {
        this.unitsCode = unitsCode;
        this.index = index;
    }
}