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
    public static float[] spriteColorArray = {
            40/255F, 40/255F, 40/255F, 1.0F,    //ID: 0, Material: void, Color: dark gray.
            116/255F, 204/255F, 244/255F, 1.0F  //ID: 1, Material: water, Color: light blue.
    };
}