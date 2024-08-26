package co.zelez.core.common;

public class Util {
    private Util() {}
    public static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
