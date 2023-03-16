package GUI;

import Util.*;
import GUI.Scene.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private static Window window = null;

    private static Scene currentScene;

    private int screenWidth;
    private int screenHeight;
    private int pixelSize;
    private String screenTitle;
    public float r, g, b, a;

    private long glfwWindow;

    private Window() {
        this.screenWidth = 1000;
        this.screenHeight = 1000;
        this.pixelSize = 10;
        this.screenTitle = "Mace Engine";
        r = 40/255F;
        b = 40/255F;
        g = 40/255F;
        a = 1;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new MainScene();
                currentScene.init();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
    }
    public void run() {
        init();
        loop();

        // Free the memory.
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback.
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public void init() {
        //Setup an error callback.
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW.
        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        //Configure GLFW.
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        //Create the window.
        glfwWindow = glfwCreateWindow(this.screenWidth, this.screenHeight, this.screenTitle, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        //Make the OpenGL context current.
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync.
        glfwSwapInterval(1);

        //Make the window visible.
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        Window.changeScene(0);
    }
    public void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt;

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events.
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            endTime = Time.getTime();
            dt = endTime - beginTime;

            if (dt >= 1F) {
                currentScene.update();
                beginTime = Time.getTime();
            }
            currentScene.run();

            glfwSwapBuffers(glfwWindow);
        }
    }
}