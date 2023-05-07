package GUI.GUI_Objects.MatrixObject.Physics.Gas;

import GUI.GUI_Objects.MatrixObject.Physics.Material;

public class Void_Gas_1 extends Material {
    private static final float[] color = {40/255F, 40/255F, 40/255F, 1.0F};
    private static final int material_ID = 1;

    public float[] getColor() {
        return color;
    }
    public int getMaterial_ID() {
        return material_ID;
    }

    public int[] getPosChange(int[][] surroundingSprites) {
        int[] posChange = new int[2];

        if (surroundingSprites[1][0] != 0) {
            posChange[1]--;
        } else if (surroundingSprites[0][1] != 0) {
            posChange[0]--;
        } else if (surroundingSprites[2][1] != 0) {
            posChange[0]++;
        }
        return posChange;
    }
}