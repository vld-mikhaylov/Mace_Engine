/*
 ** -------------------------------------------RenderMatrix----------------------------------------------------
 ** This class send all needed parameters to GPU via OpenGL to create image in the window frame.
 ** -----------------------------------------------------------------------------------------------------------
 ** init() - compile shaders, creates vertex and indices buffers and transform them in graphical objects.
 ** update() - update any changes in vertex parameters (mostly colors) and upload them to GPU.
 ** render() - use shader program to render data which allocated in vertex and vertices arrays.
 ** -----------------------------------------------------------------------------------------------------------
 ** OTHER CLASS USAGE: Shader, Temp_MatrixObject.
 ** PATTERN: None.
 */

package GUI.GUI_Objects.MatrixObject;

import Temp.Temp_MatrixObject;
import Shader.Shader;

import org.lwjgl.*;
import java.nio.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderMatrix {
    /** Shader instance for RenderMatrix class.*/
    private Shader currentShader;
    /** Buffer which is constantly uploading to GPU to set colors, and their positions.*/
    private FloatBuffer vertexBuffer;
    /** Buffer which is constantly uploading to GPU to read correctly each vertice.*/
    private IntBuffer indicesBuffer;
    /** Unique number of each shader object to render correctly.*/
    private int VAO_ID, VBO_ID, IBO_ID;
    /** Amount of position attributes in 1 vertice.*/
    private final int positionsSize = 2;
    /** Amount of color attributes in 1 vertice.*/
    private final int colorSize = 4;
    /** Amount of bytes in the float type of data.*/
    private final int floatSizeBytes = 4;
    /** Amount of bytes to create 1 vertice.*/
    private final int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;

    public RenderMatrix() {
        currentShader = new Shader("assets/shaders/Default.glsl");
    }
    /** Compile shaders, creates vertex and indices buffers and transform them in graphical objects.*/
    public void init() {
        // Compiling shaders.
        currentShader.compile();

        // Fill a float vertex buffer with coordinates and colors of vertex.
        vertexBuffer = BufferUtils.createFloatBuffer(Temp_MatrixObject.vertexArray.length);
        vertexBuffer.put(Temp_MatrixObject.vertexArray).flip();

        // Fill the int indices buffer with indices of the vertex.
        indicesBuffer = BufferUtils.createIntBuffer(Temp_MatrixObject.indicesArray.length);
        indicesBuffer.put(Temp_MatrixObject.indicesArray).flip();

        // Create VAO.
        VAO_ID = glGenVertexArrays();
        glBindVertexArray(VAO_ID);

        // Create VBO.
        VBO_ID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO_ID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);

        // Create IBO.
        IBO_ID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO_ID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers.
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
    }

    /** Update any changes in vertex parameters (mostly colors) and upload them to GPU.*/
    public void update() {
        // Update a float vertex buffer with new data.
        vertexBuffer.clear();
        vertexBuffer.put(Temp_MatrixObject.vertexArray).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
    }

    /** Use shader program to render data which allocated in vertex and vertices arrays.*/
    public void render() {
        // Bind shader program
        currentShader.use();

        // Bind the VAO.
        glBindVertexArray(VAO_ID);

        // Enable the vertex attribute pointers.
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw the triangles by the points.
        glDrawElements(GL_TRIANGLES, Temp_MatrixObject.indicesArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        // Unbind shader program.
        currentShader.detach();
    }
}