package Shader;

import java.io.*;
import java.nio.file.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    /** Unique key of shader program.*/
    private int shader;

    /** Source to get GLSL shader's vertex program.*/
    private String vertexSource;
    /** Source to get GLSL shader's fragment program.*/
    private String fragmentSource;
    /** Source to get GLSL shader.*/
    private String filePath;

    public Shader(String filePath) {
        this.filePath = filePath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            // Find the first pattern after #type 'pattern'.
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();

            // Find the second pattern after #type 'pattern'.
            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected token '" + firstPattern + "'");
            }

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected token '" + secondPattern + "'");
            }
        } catch(IOException e) {
            e.printStackTrace();
            assert false : "Error: Could not open file for shader: '" + filePath + "'";
        }
    }
    public void init() {
        int vertex, fragment;

        // First load and compile the vertex shader.
        vertex = glCreateShader(GL_VERTEX_SHADER);
        // Pass the shader source to the GPU.
        glShaderSource(vertex, vertexSource);
        glCompileShader(vertex);

        // Check for errors in compilation.
        int success = glGetShaderi(vertex, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertex, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filePath + "'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertex, len));
            assert false : "";
        }

        // First load and compile the vertex shader.
        fragment = glCreateShader(GL_FRAGMENT_SHADER);
        // Pass the shader source to the GPU.
        glShaderSource(fragment, fragmentSource);
        glCompileShader(fragment);

        // Check for errors in compilation.
        success = glGetShaderi(fragment, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragment, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filePath + "'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragment, len));
            assert false : "";
        }

        // Link shaders and check for errors.
        shader = glCreateProgram();
        glAttachShader(shader, vertex);
        glAttachShader(shader, fragment);
        glLinkProgram(shader);

        // Check for linking errors.
        success = glGetProgrami(shader, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shader, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: '" + filePath + "'\n\tLinking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shader, len));
            assert false : "";
        }
    }

    /** Bind shader program.*/
    public void attach() {
        // Bind shader program.
        glUseProgram(shader);
    }
    /** Unbind shader program.*/
    public void detach() {
        // Unbind shader program.
        glUseProgram(0);
    }
}