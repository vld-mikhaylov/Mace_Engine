package Core;

public class SpriteColorRecords {
    private float[] spriteColorArray = {
            1.0F, 1.0F, 1.0F, 0.0F,             //ID: 0, Material: void, Color: full transparent black.
            116/255F, 204/255F, 244/255F, 0.5F  //ID: 1, Material: water, Color: half transparent light blue.
    };
    public float getColor(int materialID, int elementID) {
        float[] requestColor = new float[4];
        int pointer = 0;
        for (int i = 0 + (4 * materialID); i < 4; i++) {
            requestColor[pointer] = spriteColorArray[i];
            pointer++;
        }
        return requestColor[elementID];
    }
}
