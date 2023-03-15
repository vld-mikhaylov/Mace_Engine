package Core;

public class Matrix {
    public int matrixWidth, matrixHeight, pixelSize;
    private int[][] spriteMatrix;

    public Matrix(int screenWidth, int screenHeight, int pixelSize) {
        this.pixelSize = pixelSize;
        matrixWidth = screenWidth / this.pixelSize;
        matrixHeight = screenHeight / this.pixelSize;
        init();
    }
    public void init() {
        spriteMatrix = new int[matrixWidth][matrixHeight];
        for (int w = 0; w < matrixWidth; w++) {
            for (int h = 0; h < matrixHeight; h++) {
                spriteMatrix[w][h] = 0;
            }
        }
    }
    public float getVertex(int xPos, int yPos, int elementID) {
        SpriteColorRecords spriteColorRecords = new SpriteColorRecords();
        float[] vertexArray = new float[24];
        float stepWidth = (float) 2 / matrixWidth;
        float stepHeight = (float) 2 / matrixHeight;
        int pointer = 0;

        for (int i = 0; i < 4; i++) {
            if (i == 0 || i == 2) {
                vertexArray[pointer] = (float) -1 + xPos * stepWidth;
                pointer++;
            }
            else {
                vertexArray[pointer] = (float) -1 + (xPos+1) * stepWidth;
                pointer++;
            }
            if (i == 0 || i == 1) {
                vertexArray[pointer] = (float) 1 - yPos * stepHeight;
                pointer++;
            }
            else {
                vertexArray[pointer] = (float) 1 - (yPos+1) * stepHeight;
                pointer++;
            }

            for (int j = 0; j < 4; j++) {
                vertexArray[pointer] = spriteColorRecords.getColor(spriteMatrix[xPos][yPos], j);
                pointer++;
            }
        }

        return vertexArray[elementID];
    }
    public int getElement(int xPos, int yPos, int elementID) {
        int[] elementArray = new int[6];

        elementArray[0] = 1 + (4 * ((xPos * matrixHeight) + yPos));
        elementArray[1] = (4 * ((xPos * matrixHeight) + yPos));
        elementArray[2] = 3 + (4 * ((xPos * matrixHeight) + yPos));
        elementArray[3] = 3 + (4 * ((xPos * matrixHeight) + yPos));
        elementArray[4] = (4 * ((xPos * matrixHeight) + yPos));
        elementArray[5] = 2 + (4 * ((xPos * matrixHeight) + yPos));

        return elementArray[elementID];
    }
}
