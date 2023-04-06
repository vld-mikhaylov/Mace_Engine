/*
 ** ----------------------------------------------Matrix-------------------------------------------------------
 ** This class send all needed parameters to GPU via OpenGL to create image in the window frame.
 ** -----------------------------------------------------------------------------------------------------------
 ** init() - fill matrix and send vertex and indices data to Temp class.
 ** update() - change sprite's values if there any input callback and update vertex array in Temp class.
 ** getVertex() - return full vertex array of the matrix.
 ** getIndices() - return full indices array of the matrix.
 ** getPosition() - return position parameters of 1 sprite.
 ** getColor() - return color parameters of 1 sprite.
 ** vertexRewrite() - rewrite one of the parameters of any sprite in the matrix.
 ** -----------------------------------------------------------------------------------------------------------
 ** OTHER CLASS USAGE: ConfigValues, Temp_MatrixObject.
 ** PATTERN: None.
 */

package GUI.GUI_Objects.MatrixObject;

import Config.ConfigValues;
import Temp.Temp_MatrixObject;

public class Matrix {
    /** Matrix width calculated by it's allocated in ConfigValues width divided by sprite size.*/
    public int matrixWidth;
    /** Matrix height calculated by it's allocated in ConfigValues height divided by sprite size.*/
    public int matrixHeight;
    /** Matrix which consist each sprite's color information.*/
    private int[][] materialMatrix;
    /** Amount of vertex attribute parameters in 1 sprite.*/
    private final int vertexDataAmount = 24;
    /** Amount of indices in 1 sprite.*/
    private final int indicesDataAmount = 6;

    public Matrix() {
        matrixWidth = (ConfigValues.matrixWidth[1] - ConfigValues.matrixWidth[0]) / ConfigValues.spriteSize;
        matrixHeight = (ConfigValues.matrixHeight[1] - ConfigValues.matrixHeight[0]) / ConfigValues.spriteSize;
        materialMatrix = new int[matrixWidth][matrixHeight];
    }
    /** Fill matrix and send vertex and indices data to Temp class.*/
    public void init() {
        // Set sprites colors in matrix to 0.
        for (int w = 0; w < matrixWidth; w++) {
            for (int h = 0; h < matrixHeight; h++) {
                materialMatrix[w][h] = 0;
            }
        }

        // Fill vertex and indices array in Temp with actual data.
        Temp_MatrixObject.vertexArray = getVertex();
        Temp_MatrixObject.indicesArray = getIndices();
    }

    /** Change sprite's values if there any input callback and update vertex array in Temp class.*/
    public boolean update() {
        // Checks user mouse input and apply changes in matrix and vertex by changing color parameters.
        if (Temp_MatrixObject.matrixInputData.size() >= 3) {
            int xPos = (int) Temp_MatrixObject.matrixInputData.removeFirst();
            int yPos = (int) Temp_MatrixObject.matrixInputData.removeFirst();
            int material_id = (int) Temp_MatrixObject.matrixInputData.removeFirst();
            materialMatrix[xPos][yPos] = material_id;

            vertexRewrite(xPos, yPos, "Color", getColor(xPos, yPos));
            return true;
        }
        return false;
    }

    /** Return full vertex array of the matrix.*/
    public float[] getVertex() {
        float[] vertexArray = new float[vertexDataAmount * matrixWidth * matrixHeight];

        // Fill vertex array with data by using sprite position in matrix, and it's color.
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
    /** Return full indices array of the matrix.*/
    public int[] getIndices() {
        int[] indicesArray = new int[indicesDataAmount * matrixWidth * matrixHeight];

        // Fill vertices array with data by using sprite position in matrix.
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
    /** Return position parameters of 1 sprite.*/
    public float[] getPosition(int xPos, int yPos) {
        float[] positionArray = new float[8];
        float stepWidth = 2F / (float) (ConfigValues.windowWidth / ConfigValues.spriteSize);
        float stepHeight = 2F / (float) (ConfigValues.windowHeight / ConfigValues.spriteSize);

        // Algorithms to get X and Y coordinates of each vertice of the sprite.
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
    /** Return color parameters of 1 sprite.*/
    public float[] getColor(int xPos, int yPos) {
        float[] colorArray = new float[16];

        // Algorithm to get color from each vertices of the sprite.
        int arrayPointer = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                colorArray[arrayPointer] = getMaterialColor(materialMatrix[xPos][yPos], j);
                arrayPointer++;
            }
        }
        return colorArray;
    }
    /** Return RGBA color of the material.*/
    public float getMaterialColor(int materialID, int elementID) {
        float[] requestColor = new float[4];

        // Return color's values from ConfigFile's array.
        int pointer = 0;
        for (int i = (4 * materialID); i < (4 * materialID) + 4; i++) {
            requestColor[pointer] = ConfigValues.spriteColorArray[i];
            pointer++;
        }
        return requestColor[elementID];
    }
    /** Rewrite one of the parameters of any sprite in the matrix.*/
    public void vertexRewrite(int xPos, int yPos, String attribute, float[] parameterArray) {
        // Checks for attribute to change, and apply new data on old one.
        int parameterPointer = 0;
        int vertex_parameterPointer = 0;
        if (attribute == "Position") {
            for (int vertice = 0; vertice < 4; vertice++) {
                for (int parameter = 0; parameter < 4; parameter++) {
                    int vertexArray_position = xPos * matrixHeight * vertexDataAmount +
                            yPos * vertexDataAmount + 4 * vertice + vertex_parameterPointer;
                    Temp_MatrixObject.vertexArray[vertexArray_position] = parameterArray[parameterPointer];
                    parameterPointer++;
                    vertex_parameterPointer++;
                }
            }
        }
        else if (attribute == "Color") {
            for (int vertice = 1; vertice <= 4; vertice++) {
                for (int parameter = 0; parameter < 4; parameter++) {
                    int vertexArray_position = xPos * matrixHeight * vertexDataAmount +
                            yPos * vertexDataAmount + 2 * vertice + vertex_parameterPointer;
                    Temp_MatrixObject.vertexArray[vertexArray_position] = parameterArray[parameterPointer];
                    parameterPointer++;
                    vertex_parameterPointer++;
                }
            }
        }
    }
}