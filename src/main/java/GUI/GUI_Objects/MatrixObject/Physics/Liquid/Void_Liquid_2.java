package GUI.GUI_Objects.MatrixObject.Physics.Liquid;

import GUI.GUI_Objects.MatrixObject.Physics.Material;

public class Void_Liquid_2 extends Material {
    private static final float[] color = {40/255F, 40/255F, 40/255F, 1.0F};
    private static final int material = 2;
    private static final int density = Integer.MIN_VALUE;

    public float[] getColor() {
        return color;
    }
    public int getMaterial() {
        return material;
    }
    public int getDensity() {
        return density;
    }

    public int[] getPosChange(boolean[][] surroundingSprites) {
        return new int[2];
    }
}