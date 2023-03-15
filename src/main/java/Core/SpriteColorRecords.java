package Core;

public class SpriteColorRecords {
    // Array of all possible colors of the sprites and background.
    private float[] spriteColorArray = {
            40/255F, 40/255F, 40/255F, 1.0F,    //ID: 0, Material: void, Color: black gray.
            116/255F, 204/255F, 244/255F, 1.0F  //ID: 1, Material: water, Color: light blue.
    };



    public float getColor(int materialID, int elementID) {
        float[] requestColor = new float[4];

        // Algorithm to return RGBA color of the vertice.
        int pointer = 0;
        for (int i = (4 * materialID); i < (4 * materialID) + 4; i++) {
            requestColor[pointer] = spriteColorArray[i];
            pointer++;
        }
        return requestColor[elementID];
    }
}
