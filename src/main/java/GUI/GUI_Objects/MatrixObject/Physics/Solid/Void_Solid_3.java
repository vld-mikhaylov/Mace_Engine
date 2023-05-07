package GUI.GUI_Objects.MatrixObject.Physics.Solid;

import GUI.GUI_Objects.MatrixObject.Physics.Material;

public class Void_Solid_3 extends Material {
    private static final float[] color = {40/255F, 40/255F, 40/255F, 1.0F};
    private static final int material_ID = 3;

    public float[] getColor() {
        return color;
    }
    public int getMaterial_ID() {
        return material_ID;
    }

    public int[] getPosChange(int[][] surroundingSprites) {
        return new int[2];
    }
}
