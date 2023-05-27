package GUI.GUI_Objects.MatrixObject.Physics.Gas;
import GUI.GUI_Objects.MatrixObject.Physics.Material;

public class Water_Gas_11 extends Material {
    private static final float[] color = {200 / 255F, 225 / 255F, 230 / 255F, 1.0F};
    private static final int material = 11;
    private static final int density = 59;

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

        if (surroundingSprites[1][0]) {
            posChange[1]--;
        } else if (surroundingSprites[0][2] && surroundingSprites[2][2]) {
            posChange[0] = (int) Math.round(2 * Math.random() - 1);
            posChange[1]++;
        } else if (surroundingSprites[0][0]) {
            posChange[0]--;
            posChange[1]--;
        } else if (surroundingSprites[2][0]) {
            posChange[0]++;
            posChange[1]--;
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