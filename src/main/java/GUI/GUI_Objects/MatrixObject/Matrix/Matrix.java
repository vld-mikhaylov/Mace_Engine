package GUI.GUI_Objects.MatrixObject.Matrix;

import Config.ConfigValues;
import GUI.GUI_Objects.MatrixObject.Physics.Material;
import GUI.GUI_Objects.MatrixObject.Physics.MaterialRecord;
import GUI.Render;

import java.util.concurrent.CopyOnWriteArrayList;

public class Matrix {
    /** The width of the matrix which is calculated by dividing the width of the window by the size of each sprite.*/
    public static int matrixWidth;
    /** The height of the matrix which is calculated by dividing the height of the window by the size of each sprite.*/
    public static int matrixHeight;
    /** An array containing the rendering and physics parameters of each sprite.*/
    public volatile static int[][][] matrix;

    /** The list containing user-entered sprite parameters to be replaced in the matrix.*/
    public volatile static CopyOnWriteArrayList inputData;
    /** The list containing the sprite parameters changed by the physics algorithm, which must be replaced in the matrix.*/
    public volatile static CopyOnWriteArrayList physicData;

    public Matrix() {
        matrixWidth = (ConfigValues.matrixWidth[1] - ConfigValues.matrixWidth[0]) / ConfigValues.spriteSize;
        matrixHeight = (ConfigValues.matrixHeight[1] - ConfigValues.matrixHeight[0]) / ConfigValues.spriteSize;
        matrix = new int[matrixWidth][matrixHeight][2];

        inputData = new CopyOnWriteArrayList<>();
        physicData = new CopyOnWriteArrayList<>();
    }
    public void init() {
        // Set sprites colors in matrix to 0.
        for (int w = 0; w < matrixWidth; w++) {
            for (int h = 0; h < matrixHeight; h++) {
                matrix[w][h][0] = 3;
                matrix[w][h][1] = 0;
            }
        }

        // Fill vertex and indices arrays with actual data.
        Render.vertexArray = getVertex();
        Render.indexArray = getIndices();
    }

    /** Checks for changes in inputData list and physicData list.*/
    public void run() {
        // Check user input changes and apply them to matrix.
        if (inputData.size() >= 3) {
            while (inputData.size() != 0) {
                int xPos = (int) inputData.remove(0);
                int yPos = (int) inputData.remove(0);
                matrix[xPos][yPos][0] = (int) inputData.remove(0);
                matrix[xPos][yPos][1] = 0;

                vertexRewrite(xPos, yPos, "Color", getColor(xPos, yPos));
                Render.isVertexArrayUpdated = true;
            }
        }

        // Check physic changes and apply them to matrix.
        if (physicData.size() >= 4) {
            while (physicData.size() != 0) {
                int xPos = (int) physicData.remove(0);
                int yPos = (int) physicData.remove(0);
                matrix[xPos][yPos][0] = (int) physicData.remove(0);
                matrix[xPos][yPos][1] = (int) physicData.remove(0);

                vertexRewrite(xPos, yPos, "Color", getColor(xPos, yPos));
                Render.isVertexArrayUpdated = true;
            }
        }
    }

    /** Overwrites the value of any sprite in vertex array.*/
    public void vertexRewrite(int xPos, int yPos, String attribute, float[] parameterArray) {
        // Changes the value of the sprite's position in the vertex array.
        int parameterPointer = 0;
        int vertex_parameterPointer = 0;
        if (attribute.equals("Position")) {
            for (int vertice = 0; vertice < 4; vertice++) {
                for (int parameter = 0; parameter < 4; parameter++) {
                    int vertexArray_position = xPos * matrixHeight * 24 +
                            yPos * 24 + 4 * vertice + vertex_parameterPointer;
                    Render.vertexArray[vertexArray_position] = parameterArray[parameterPointer];
                    parameterPointer++;
                    vertex_parameterPointer++;
                }
            }
        }
        // Changes the value of the sprite's color in the vertex array.
        else if (attribute.equals("Color")) {
            for (int vertice = 1; vertice <= 4; vertice++) {
                for (int parameter = 0; parameter < 4; parameter++) {
                    int vertexArray_position = xPos * matrixHeight * 24 +
                            yPos * 24 + 2 * vertice + vertex_parameterPointer;
                    Render.vertexArray[vertexArray_position] = parameterArray[parameterPointer];
                    parameterPointer++;
                    vertex_parameterPointer++;
                }
            }
        }
    }
    /** Returns the vertex array of the matrix.*/
    public float[] getVertex() {
        float[] vertexArray = new float[24 * matrixWidth * matrixHeight];

        // Creates a vertex array with the values of each sprite parameter.
        int parameterPointer = 0;
        for (int sprite_Xid = 0; sprite_Xid < matrixWidth; sprite_Xid++) {
            for (int sprite_Yid = 0; sprite_Yid < matrixHeight; sprite_Yid++) {
                for (int sprite_vertice = 0; sprite_vertice < 4; sprite_vertice++) {
                    for (int vertice_position = 0; vertice_position < 2; vertice_position++) {
                        vertexArray[parameterPointer] = getPosition(sprite_Xid, sprite_Yid)[vertice_position + sprite_vertice * 2];
                        parameterPointer++;
                    }
                    for (int vertice_color = 0; vertice_color < 4; vertice_color++) {
                        vertexArray[parameterPointer] = getColor(sprite_Xid, sprite_Yid)[vertice_color + sprite_vertice * 4];
                        parameterPointer++;
                    }
                }
            }
        }
        return vertexArray;
    }
    /** Return the index array of the matrix.*/
    public int[] getIndices() {
        int[] indicesArray = new int[6 * matrixWidth * matrixHeight];

        // Creates an index array with the values of each sprite pointers.
        int parameterPointer = 0;
        for (int sprite_Xid = 0; sprite_Xid < matrixWidth; sprite_Xid++) {
            for (int sprite_Yid = 0; sprite_Yid < matrixHeight; sprite_Yid++) {
                indicesArray[parameterPointer] = 1 + (4 * ((sprite_Xid * matrixHeight) + sprite_Yid));
                parameterPointer++;
                indicesArray[parameterPointer] = (4 * ((sprite_Xid * matrixHeight) + sprite_Yid));
                parameterPointer++;
                indicesArray[parameterPointer] = 3 + (4 * ((sprite_Xid * matrixHeight) + sprite_Yid));
                parameterPointer++;
                indicesArray[parameterPointer] = 3 + (4 * ((sprite_Xid * matrixHeight) + sprite_Yid));
                parameterPointer++;
                indicesArray[parameterPointer] = (4 * ((sprite_Xid * matrixHeight) + sprite_Yid));
                parameterPointer++;
                indicesArray[parameterPointer] = 2 + (4 * ((sprite_Xid * matrixHeight) + sprite_Yid));
                parameterPointer++;
            }
        }
        return indicesArray;
    }

    /** Returns the position parameters of any sprite.*/
    public float[] getPosition(int xPos, int yPos) {
        float[] positionArray = new float[8];
        float stepWidth = 2F / (float) (ConfigValues.windowWidth / ConfigValues.spriteSize);
        float stepHeight = 2F / (float) (ConfigValues.windowHeight / ConfigValues.spriteSize);

        // Returns the value of the position of any sprite point relative to the window's coordinate system.
        int arrayPointer = 0;
        for (int i = 0; i < 4; i++) {
            if (i == 0 || i == 2) {
                positionArray[arrayPointer] = (-1 + (2F * (ConfigValues.matrixWidth[0]) / ConfigValues.windowWidth) + (xPos * stepWidth));
                arrayPointer++;
            }
            else {
                positionArray[arrayPointer] = (-1 + (2F * (ConfigValues.matrixWidth[0]) / ConfigValues.windowWidth) + ((xPos+1) * stepWidth));
                arrayPointer++;
            }
            if (i == 0 || i == 1) {
                positionArray[arrayPointer] = (1 - (2F * (ConfigValues.matrixHeight[0]) / ConfigValues.windowHeight) - (yPos * stepHeight));
                arrayPointer++;
            }
            else {
                positionArray[arrayPointer] = (1 - (2F * (ConfigValues.matrixHeight[0]) / ConfigValues.windowHeight) - ((yPos+1) * stepHeight));
                arrayPointer++;
            }
        }
        return positionArray;
    }
    /** Returns the color parameters of any sprite.*/
    public float[] getColor(int xPos, int yPos) {
        float[] colorArray = new float[16];

        // Returns the color value of any sprite point based on its material.
        int arrayPointer = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                colorArray[arrayPointer] = getMaterialColor(matrix[xPos][yPos][0], j);
                arrayPointer++;
            }
        }
        return colorArray;
    }

    /** Returns the color array of any material.*/
    public float getMaterialColor(int material, int element) {
        Material materialInstance = MaterialRecord.getInstance(material);
        float[] requestColor = materialInstance.getColor();
        return requestColor[element];
    }
}
