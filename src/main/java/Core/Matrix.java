package Core;

public class Matrix {
    public int matrixWidth, matrixHeight, spriteSize;    // Width and height of the matrix, and size of each sprite.
    private int[][] spriteMatrix;                        // Matrix which contain all sprites.
    private float[] vertexArray;                         // Array of coordinates and colors for the 1 sprite.



    public Matrix(int screenWidth, int screenHeight, int pixelSize) {
        this.spriteSize = pixelSize;
        matrixWidth = screenWidth / this.spriteSize;
        matrixHeight = screenHeight / this.spriteSize;

        init();
    }

    public void init() {
        // Initialise matrix and it's sprites color to 1 (void).
        spriteMatrix = new int[matrixWidth][matrixHeight];
        for (int w = 0; w < matrixWidth; w++) {
            for (int h = 0; h < matrixHeight; h++) {
                spriteMatrix[w][h] = (int) (Math.round(Math.random()));
            }
        }
    }
    public float[] getVertex(int xPos, int yPos) {
        SpriteColorRecords spriteColorRecords = new SpriteColorRecords();
        vertexArray = new float[24];
        float stepWidth = (float) 2 / matrixWidth;      // Parametric value to set correctly X borders of the sprite.
        float stepHeight = (float) 2 / matrixHeight;    // Parametric value to set correctly Y borders of the sprite.
        int vertexPointer = 0;

        for (int i = 0; i < 4; i++) {
            // Algorithm to set X coordinates for each vertices of the sprite.
            if (i == 0 || i == 2) {
                vertexArray[vertexPointer] = (float) -1 + xPos * stepWidth;
                vertexPointer++;
            }
            else {
                vertexArray[vertexPointer] = (float) -1 + (xPos+1) * stepWidth;
                vertexPointer++;
            }

            // Algorithm to set Y coordinates for each vertices of the sprite.
            if (i == 0 || i == 1) {
                vertexArray[vertexPointer] = (float) 1 - yPos * stepHeight;
                vertexPointer++;
            }
            else {
                vertexArray[vertexPointer] = (float) 1 - (yPos+1) * stepHeight;
                vertexPointer++;
            }

            // Algorithm to set colors for each vertices of the sprite.
            for (int j = 0; j < 4; j++) {
                vertexArray[vertexPointer] = spriteColorRecords.getColor(spriteMatrix[xPos][yPos], j);
                vertexPointer++;
            }
        }

        return vertexArray;
    }
    public int[] getElement(int xPos, int yPos) {
        int[] elementArray = new int[6];

        // Algorithm to create right indices for elementArray.
        //  0          1
        //
        //
        //  2          3
        //
        // |1 -> 0 -> 3|  |3 -> 0 -> 2|
        elementArray[0] = 5 + (4 * ((xPos * matrixHeight) + yPos));
        elementArray[1] = 4 + (4 * ((xPos * matrixHeight) + yPos));
        elementArray[2] = 7 + (4 * ((xPos * matrixHeight) + yPos));
        elementArray[3] = 7 + (4 * ((xPos * matrixHeight) + yPos));
        elementArray[4] = 4 + (4 * ((xPos * matrixHeight) + yPos));
        elementArray[5] = 6 + (4 * ((xPos * matrixHeight) + yPos));

        return elementArray;
    }
}
