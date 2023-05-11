package GUI;

import Shader.Shader;

import org.lwjgl.*;
import java.nio.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Render {
    /** An array of all the data needed to create and update a VBO.*/
    public volatile static float[] vertexArray;
    /** A data area that stores information about all or several attributes of a vertex.*/
    private FloatBuffer vertexBuffer;
    /** An array of all the data needed to create and update a IBO.*/
    public volatile static int[] indexArray;
    /** An array of pointers into the vertex buffer.*/
    private IntBuffer indexBuffer;

    /** A variable that expresses the sameness of data in the vertex buffer and vertex array.*/
    public volatile static boolean isVertexArrayUpdated;
    /** A variable that expresses the sameness of data in the index buffer and index array.*/
    public volatile static boolean isIndexArrayUpdated;

    /** A Vertex Array Object is an OpenGL Object that stores all of the state needed to supply vertex data.*/
    private int VAO_ID;
    /** A Vertex Buffer Object is a buffer object which is used as a source for vertex array data.*/
    private int VBO_ID;
    /** An Index Buffer Object is a buffer object which is used as a source for index array data.*/
    private int IBO_ID;

    /** A sample of the Shader class required for calculating rendering effects.*/
    private Shader shaderInstance;

    public Render() {
        shaderInstance = new Shader("assets/shaders/Default.glsl");
        isVertexArrayUpdated = false;
        isIndexArrayUpdated = false;
    }
    public void init() {
        // Compiling shaders.
        shaderInstance.init();

        // Fill vertex buffer with coordinates and colors of vertex.
        vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Fill index buffer with the pointers of the vertex.
        indexBuffer = BufferUtils.createIntBuffer(indexArray.length);
        indexBuffer.put(indexArray).flip();

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
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers.
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 24, 0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 24, 8);
    }

    /** Updates buffer objects if data values have changed and renders one frame.*/
    public void run() {
        // Update the vertex buffer with new data from the vertex array.
        if (isVertexArrayUpdated) {
            vertexBuffer.clear();
            vertexBuffer.put(vertexArray).flip();
            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
            isVertexArrayUpdated = false;
        }

        // Bind shader program.
        shaderInstance.attach();

        // Bind the VAO.
        glBindVertexArray(VAO_ID);

        // Enable the vertex attribute pointers.
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Draw the triangles.
        glDrawElements(GL_TRIANGLES, indexArray.length, GL_UNSIGNED_INT, 0);

        // Disable vertex attribute pointers and unbind the VAO.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        // Unbind shader program.
        shaderInstance.detach();
    }
}