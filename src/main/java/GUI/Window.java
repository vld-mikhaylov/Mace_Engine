package GUI;

import Config.ConfigValues;
import GUI.GUI_Objects.ThreadManager;
import Util.KeyboardControl;
import Util.MouseControl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    /** As the window and context are inseparably linked, this object is used as both a context and window handle.*/
    public static long window;

    /** The KeyboardControl class instance is required to read the user's key status.*/
    private KeyboardControl keyboardControlInstance;
    /** The MouseControl class instance is required to read the user's mouse movements.*/
    private MouseControl mouseControlInstance;
    /** An instance of the ThreadManager class is required to correctly render and processing of GUI objects.*/
    private ThreadManager threadManagerInstance;
    /** An instance of the Render class is required to display an image on the screen by passing vertex data to the GPU.*/
    private Render renderInstance;

    public Window() {
        keyboardControlInstance = new KeyboardControl();
        mouseControlInstance = new MouseControl();
        threadManagerInstance = new ThreadManager();
        renderInstance = new Render();
    }
    public void init() {
        // Setup an error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW.
        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW window frame.
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // Create the window.
        window = glfwCreateWindow(ConfigValues.windowWidth, ConfigValues.windowHeight, ConfigValues.windowTitle, NULL, NULL);
        if (window == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // Make the OpenGL context current.
        glfwMakeContextCurrent(window);

        // Disable v-sync.
        glfwSwapInterval(1);

        // Make the window visible.
        glfwShowWindow(window);

        // LWJGL detect the context and make the OpenGL bindings available for use.
        GL.createCapabilities();

        // Initialise instances.
        keyboardControlInstance.init();
        mouseControlInstance.init();
        threadManagerInstance.init();
        renderInstance.init();
    }

    /** Initialise window frame and render events while not closed.*/
    public void run() {
        // Start parallel threads of GUI objects.
        threadManagerInstance.setThreadStatus(0, 1);
        threadManagerInstance.run();
        while (!glfwWindowShouldClose(window)) {
            // Poll all window events.
            glfwPollEvents();

            // Set color of the window to black and clear color buffer.
            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            // Read and render all events.
            keyboardControlInstance.run();
            mouseControlInstance.run();
            renderInstance.run();

            // Swap the window's buffers.
            glfwSwapBuffers(window);
        }
        // Wait for all threads to end.
        threadManagerInstance.end();

        // Free the memory.
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and the free the error callback.
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}