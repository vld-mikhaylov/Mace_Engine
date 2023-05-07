package GUI.GUI_Objects.MatrixObject.Physics;

public abstract class Material {
    private static float[] color;
    private static int material_ID;

    public abstract float[] getColor();
    public abstract int getMaterial_ID();

    public abstract int[] getPosChange(int surroundingSprites[][]);
}