package Core;

public class Matrix {
    /** Matrix width calculated by division of screen width and size of the sprite.*/
    public int matrixWidth;
    /** Matrix height calculated by division of screen height and size of the sprite.*/
    public int matrixHeight;
    /** Size of each sprite.*/
    public int spriteSize;
    /** Matrix which contain all sprite's material ID.*/
    private int[][] spriteMatrix;

    public Matrix(int screenWidth, int screenHeight, int pixelSize) {
        this.spriteSize = pixelSize;
        matrixWidth = screenWidth / this.spriteSize;
        matrixHeight = screenHeight / this.spriteSize;
    }

    /** Initialize matrix and set all sprites color to 0 (Void).*/
    public void init() {
        spriteMatrix = new int[matrixWidth][matrixHeight];
        for (int w = 0; w < matrixWidth; w++) {
            for (int h = 0; h < matrixHeight; h++) {
                spriteMatrix[w][h] = (int) Math.round(Math.random());
            }
        }
    }
    /** Return array with all matrix's vertex parameters.*/
    public float[] getVertex(int xPos, int yPos) {
        float[] vertexArray       = new float[24];
        float[] tempPositionArray = getPosition(xPos, yPos);
        float[] tempColorArray    = getColor(xPos, yPos);

        int verticePointer  = 0;
        int positionPointer = 0;
        int colorPointer    = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                vertexArray[verticePointer] = tempPositionArray[positionPointer];
                verticePointer++;
                positionPointer++;
            }
            for (int j = 0; j < 4; j++) {
                vertexArray[verticePointer] = tempColorArray[colorPointer];
                verticePointer++;
                colorPointer++;
            }
        }

        return vertexArray;
    }
    /** Return position parameters.*/
    public float[] getPosition(int xPos, int yPos) {
        float[] positionArray = new float[8];
        float stepWidth = (float) 2 / matrixWidth;
        float stepHeight = (float) 2 / matrixHeight;

        // Algorithms to get X and Y coordinates of each vertice of the sprite.
        int arrayPointer = 0;
        for (int i = 0; i < 4; i++) {
            if (i == 0 || i == 2) {
                positionArray[arrayPointer] = (float) -1 + xPos * stepWidth;
                arrayPointer++;
            }
            else {
                positionArray[arrayPointer] = (float) -1 + (xPos+1) * stepWidth;
                arrayPointer++;
            }
            if (i == 0 || i == 1) {
                positionArray[arrayPointer] = (float) 1 - yPos * stepHeight;
                arrayPointer++;
            }
            else {
                positionArray[arrayPointer] = (float) 1 - (yPos+1) * stepHeight;
                arrayPointer++;
            }
        }

        return positionArray;
    }
    /** Return color parameters.*/
    public float[] getColor(int xPos, int yPos) {
        SpriteColorRecords spriteColorRecords = new SpriteColorRecords();
        float[] colorArray = new float[16];

        // Algorithm to get color from each vertices of the sprite.
        int arrayPointer = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                colorArray[arrayPointer] = spriteColorRecords.getColor(spriteMatrix[xPos][yPos], j);
                arrayPointer++;
            }
        }

        return colorArray;
    }
    /** Set some sprite's color in matrix.*/
    public void setColor(int xPos, int yPos, int materialID) {
        spriteMatrix[xPos][yPos] = materialID;
    }
    /** Return indices to create IBO.*/
    public int[] getIndices(int xPos, int yPos) {
        int[] indicesArray = new int[6];

        indicesArray[0] = 1 + (4 * ((xPos * matrixHeight) + yPos));
        indicesArray[1] = (4 * ((xPos * matrixHeight) + yPos));
        indicesArray[2] = 3 + (4 * ((xPos * matrixHeight) + yPos));
        indicesArray[3] = 3 + (4 * ((xPos * matrixHeight) + yPos));
        indicesArray[4] = (4 * ((xPos * matrixHeight) + yPos));
        indicesArray[5] = 2 + (4 * ((xPos * matrixHeight) + yPos));

        return indicesArray;
    }
}