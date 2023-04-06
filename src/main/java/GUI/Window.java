/*
** -----------------------------------------------Window------------------------------------------------------
** GLFW window object created to setup window frame and it's callbacks (mouse, keyboard etc.).
** -----------------------------------------------------------------------------------------------------------
** getInstance() - return Window instance. (Pattern feature)
** init() - initialise window frame, MouseControl and MatrixObjects instances.
** run() - initialise window frame and render events while not closed.
** loop() - waits for events, after that swap window buffer to show rendered frame.
** -----------------------------------------------------------------------------------------------------------
** OTHER CLASS USAGE: Object, MouseControl.
** PATTERN: Singleton.
** NOTE: Keep glfwSwapInterval() to 0 and glfwWaitEvents() not glfwPullEvents,
**       to increase quality of mouse callback and PC resource usage.
*/

package GUI;

import Config.ConfigValues;
import Util.MouseControl;
import GUI.GUI_Objects.MatrixObject.MatrixObject;
import GUI.GUI_Objects.Object;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    /** Window instance to achieve singleton pattern.*/
    private static Window windowInstance;
    /** MouseControl instance for Window class.*/
    private MouseControl mouseControlInstance;
    /** Object instance for Window class. (May actually inherit many other classes)*/
    private Object objectInstance;
    /** Window frame's unique id to operate with.*/
    private long window_id;

    private Window() {}
    /** Return Window instance. (Pattern feature)*/
    public static Window getInstance() {
        if (Window.windowInstance == null) {
            Window.windowInstance = new Window();
        }
        return Window.windowInstance;
    }
    /** Initialise window frame, MouseControl and MatrixObjects instances.*/
    private void init() {
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
        window_id = glfwCreateWindow(ConfigValues.windowWidth, ConfigValues.windowHeight, ConfigValues.windowTitle, NULL, NULL);
        if (window_id == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // Make the OpenGL context current.
        glfwMakeContextCurrent(window_id);

        // Disable v-sync.
        glfwSwapInterval(0);

        // Make the window visible.
        glfwShowWindow(window_id);

        // LWJGL detects the context and makes the OpenGL bindings available for use.
        GL.createCapabilities();

        // Creates and initialise instance of MouseControl class.
        mouseControlInstance = new MouseControl(window_id);
        mouseControlInstance.init();

        // Creates and initialise instance of MatrixObject class.
        objectInstance = new MatrixObject();
        objectInstance.init();
    }

    /** Initialise window frame and render events while not closed.*/
    public void run() {
        // Initialise the window frame and setup it.
        init();

        // Render all frame's events.
        loop();

        // Free the memory.
        glfwFreeCallbacks(window_id);
        glfwDestroyWindow(window_id);

        // Terminate GLFW and the free the error callback.
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /** Waits for events, after that swap window buffer to show rendered frame.*/
    private void loop() {
        while (!glfwWindowShouldClose(window_id)) {
            // Wait all window events.
            glfwWaitEvents();

            // Set color of the window to black and clear color buffer.
            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            // Update and render all events.
            mouseControlInstance.run();
            objectInstance.run();

            // Swaps the window's buffer.
            glfwSwapBuffers(window_id);
        }
    }
}