package GUI.GUI_Objects.MatrixObject.Physics.Liquid;

import GUI.GUI_Objects.MatrixObject.Physics.Material;

public class Water_Liquid_12 extends Material {
    private static final float[] color = {116/255F, 204/255F, 244/255F, 1.0F};
    private static final int material_ID = 12;

    public float[] getColor() {
        return color;
    }
    public int getMaterial_ID() {
        return material_ID;
    }

    public int[] getPosChange(int[][] surroundingSprites) {
        int[] posChange = new int[2];

        if (surroundingSprites[1][2] == 3) {
            posChange[1]++;
        } else if (surroundingSprites[0][2] == 3) {
            posChange[0]--;
            posChange[1]++;
        } else if (surroundingSprites[2][2] == 3) {
            posChange[0]++;
            posChange[1]++;
        } else if (surroundingSprites[0][1] == 3) {
            posChange[0]--;
        } else if (surroundingSprites[2][1] == 3) {
            posChange[0]++;
        }
        return posChange;
    }
}
