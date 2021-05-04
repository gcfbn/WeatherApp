package app.mainFrame;

public class HexSpaceConverter {

    /**
     * Replaces all spaces in string with hex ASCII code of space (%20)
     */
    public static String spacesToHex(String string){
        return string.replaceAll("\\s+", "%20");
    }

    /**
     * Replaces all hex ASCII codes of space with normal spaces (" ")
     */
    public static String hexToSpaces(String string){
        return string.replaceAll("%20", " ");
    }
}
