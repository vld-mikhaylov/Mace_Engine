package Render;

import Core.Matrix;
import Core.SpriteColorRecords;
import org.lwjgl.*;
import java.nio.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Render {
    private Shader shader;   // Parameter for Shader class.
    private Matrix matrix;   // Parameter for Matrix class.

    private float[] vertexArray;        // Array of coordinates and colors for the all matrix.
    private int[] elementArray;         // Array of indices of the points to correctly read all the matrix.
    private int vaoID, vboID, eboID;    // ID of the VAO, VBO and EBO objects. Is used to upload them to GPU.

    private final int positionsSize   = 2;   // Amount of coordinates to set vertice position (2D).
    private final int colorSize       = 4;   // Amount of colors to set vertice color (RGB and Alpha channel).
    private final int floatSizeBytes  = 4;   // Amount of bytes in the float type of data (Shader program allow any to increase precision).
    private final int vertexSizeBytes =      //
            (positionsSize + colorSize)      // Amount of bytes to create 1 vertice.
                    * floatSizeBytes;        //

    private final int vertexDataAmount  = 24;   // Amount of 4 vertices float parameters.
    private final int elementDataAmount = 6;    // Amount of indices of the points to correctly read 4 vertices.



    public Render() {}

    private void set() {
        matrix       = new Matrix(1000, 1000, 10);
        vertexArray  = new float[vertexDataAmount * (matrix.matrixWidth * matrix.matrixHeight) + vertexDataAmount];
        elementArray = new int[elementDataAmount * (matrix.matrixWidth * matrix.matrixHeight)  + elementDataAmount];
        shader       = new Shader("assets/shaders/Default.glsl");

        float[] tempVertexArray;    // Temporary vertex array for 1 sprite.
        int[]   tempElementArray;   // Temporary element's indices array to read 1 sprite.

        // |-Algorithm to fill vertexArray and elementArray with all matrix's vertices and indices-|
        int vertexPointer  = 0;
        int elementPointer = 0;

        for (int column = 0; column <  matrix.matrixWidth; column++) {
            for (int row = 0; row < matrix.matrixHeight; row++) {
                tempVertexArray = matrix.getVertex(column, row);
                tempElementArray = matrix.getElement(column, row);

                for (int k = 0; k < vertexDataAmount; k++) {
                    vertexArray[vertexPointer] = tempVertexArray[k];
                    vertexPointer++;
                }
                for (int k = 0; k < elementDataAmount; k++) {
                    elementArray[elementPointer] = tempElementArray[k];
                    elementPointer++;
                }
            }
        }
        // |---------------------------------------------------------------------------------------|

        init();
    }
    private void init() {
        shader.compile();

        // Create a float buffer of coordinates and colors of vertices.
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create the indices of points to generate triangles correctly.
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        // Create VAO.
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create VBO.
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create EBO.
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers.
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }

    public void update() {
        set();

        // Bind shader program
        shader.use();

        // Bind the VAO.
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers.
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw the triangles by the points.
        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        // Unbind shader program.
        shader.detach();
    }
}
