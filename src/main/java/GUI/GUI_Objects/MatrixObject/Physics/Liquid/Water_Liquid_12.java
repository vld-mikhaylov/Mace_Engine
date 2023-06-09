package GUI.GUI_Objects.MatrixObject.Physics.Liquid;

import GUI.GUI_Objects.MatrixObject.Physics.Material;

public class Water_Liquid_12 extends Material {
    private static final float[] color = {116/255F, 204/255F, 244/255F, 1.0F};
    private static final int material = 12;
    private static final int density = 1000;

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
        } else if (surroundingSprites[0][1] && surroundingSprites[2][1]) {
            posChange[0] = (int) Math.round(2 * Math.random() - 1);
        } else if (surroundingSprites[0][1]) {
            posChange[0]--;
        } else if (surroundingSprites[2][1]) {
            posChange[0]++;
        }
        return posChange;
    }
}
