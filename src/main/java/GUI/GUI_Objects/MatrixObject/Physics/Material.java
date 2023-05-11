package GUI.GUI_Objects.MatrixObject.Physics;

public abstract class Material {
    private static float[] color;
    private static int material;
    private static int density;

    public abstract float[] getColor();
    public abstract int getMaterial();
    public abstract int getDensity();

    public abstract int[] getPosChange(boolean surroundingSprites[][]);
}