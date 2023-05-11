package GUI.GUI_Objects.MatrixObject.Physics.Solid;

import GUI.GUI_Objects.MatrixObject.Physics.Material;

public class Sand_Solid_23 extends Material {
    private static final float[] color = {244/255F, 164/255F, 96/255F, 1.0F};
    private static final int material = 23;
    private static final int density = 1700;

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
        int[] posChange = new int[2];

        if (surroundingSprites[1][2]) {
            posChange[1]++;
        } else if (surroundingSprites[0][2] && surroundingSprites[2][2]) {
            posChange[0] = (int) Math.round(2 * Math.random() - 1);
            posChange[1]++;
        } else if (surroundingSprites[0][2]) {
            posChange[0]--;
            posChange[1]++;
        } else if (surroundingSprites[2][2]) {
            posChange[0]++;
            posChange[1]++;
        }

        return posChange;
    }
}
