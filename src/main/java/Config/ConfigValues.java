package Config;

public record ConfigValues() {
    public static String windowTitle = "Mace_Engine";
    public static int windowWidth    = 1000;
    public static int windowHeight   = 1000;
    public static int spriteSize     = 10;

    public static int[] matrixWidth = {
            0, 1000
    };
    public static int[] matrixHeight = {
            0, 1000
    };
}