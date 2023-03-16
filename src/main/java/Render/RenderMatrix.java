package Render;

import Core.Matrix;
import Core.SpriteColorRecords;
import org.lwjgl.*;
import java.nio.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderMatrix {
    private Shader shader;
    private Matrix matrix;

    private FloatBuffer vertexBuffer;
    private IntBuffer indicesBuffer;
    /** Array of all matrix sprite's attribute parameters (XY Coordinates and RGBA Colors).*/
    private float[] vertexArray;
    /** Array of all matrix indices.*/
    private int[] indicesArray;
    private int VAO_ID, VBO_ID, IBO_ID;

    /** Amount of position attributes in 1 vertice.*/
    private final int positionsSize = 2;
    /** Amount of color attributes in 1 vertice.*/
    private final int colorSize = 4;
    /** Amount of bytes in the float type of data.*/
    private final int floatSizeBytes = 4;
    /** Amount of bytes to create 1 vertice.*/
    private final int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;

    /** Amount of 4 vertices float attribute parameters.*/
    private final int vertexDataAmount = 24;
    /** Amount of indices of 1 sprite.*/
    private final int indicesDataAmount = 6;

    public RenderMatrix() {
        matrix       = new Matrix(1000, 1000, 10);
        vertexArray  = new float[vertexDataAmount * (matrix.matrixWidth * matrix.matrixHeight)];
        indicesArray = new int[indicesDataAmount * (matrix.matrixWidth * matrix.matrixHeight)];
        shader       = new Shader("assets/shaders/Default.glsl");

        matrix.init();

        float[] tempVertexArray;  // Temporary vertex array of 1 sprite.
        int[]   tempIndicesArray; // Temporary indices array of 1 sprite.

        // Algorithm to fill vertexArray and indicesArray of all matrix's sprites.
        int vertexPointer  = 0;
        int indicesPointer = 0;
        for (int column = 0; column <  matrix.matrixWidth; column++) {
            for (int row = 0; row < matrix.matrixHeight; row++) {
                tempVertexArray  = matrix.getVertex(column, row);
                tempIndicesArray = matrix.getIndices(column, row);

                for (int k = 0; k < vertexDataAmount; k++) {
                    vertexArray[vertexPointer] = tempVertexArray[k];
                    vertexPointer++;
                }
                for (int k = 0; k < indicesDataAmount; k++) {
                    indicesArray[indicesPointer] = tempIndicesArray[k];
                    indicesPointer++;
                }
            }
        }
    }

    public void init() {
        shader.compile();

        // Fill a float buffer with coordinates and colors of vertices.
        vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Fill the int buffer with indices of vertices.
        indicesBuffer = BufferUtils.createIntBuffer(indicesArray.length);
        indicesBuffer.put(indicesArray).flip();

        // Create VAO.
        VAO_ID = glGenVertexArrays();
        glBindVertexArray(VAO_ID);

        // Create VBO.
        VBO_ID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO_ID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create IBO.
        IBO_ID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO_ID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers.
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
    }
    public void update() {
        float[] tempColorArray;
        matrix.init();

        int vertexPointer = positionsSize;
        int colorPointer;
        for (int column = 0; column <  matrix.matrixWidth; column++) {
            for (int row = 0; row < matrix.matrixHeight; row++) {
                tempColorArray = matrix.getColor(column, row);
                colorPointer = 0;

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < colorSize; j++) {
                        vertexArray[vertexPointer] = tempColorArray[colorPointer];
                        vertexPointer++;
                        colorPointer++;
                    }
                    vertexPointer += positionsSize;
                }
            }
        }

        // Update a float buffer of coordinates and colors of vertices.
        vertexBuffer.clear();
        vertexBuffer.put(vertexArray).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
    }
    public void run() {
        // Bind shader program
        shader.use();

        // Bind the VAO.
        glBindVertexArray(VAO_ID);

        // Enable the vertex attribute pointers.
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw the triangles by the points.
        glDrawElements(GL_TRIANGLES, indicesArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        // Unbind shader program.
        shader.detach();
    }
}