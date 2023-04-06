package Util;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
    private static MouseListener mouseListenerInstance;
    private double xPos, yPos;
    private boolean mouseButtonStatus[] = new boolean[GLFW_MOUSE_BUTTON_LAST];


    private MouseListener() {
        xPos = 0.0;
        yPos = 0.0;
    }
    public static MouseListener getInstance() {
        if (MouseListener.mouseListenerInstance == null) {
            MouseListener.mouseListenerInstance = new MouseListener();
        }

        return MouseListener.mouseListenerInstance;
    }

    public static void mousePositionCallback(long window, double xpos, double ypos) {
        getInstance().xPos = xpos;
        getInstance().yPos = ypos;
    }
    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < getInstance().mouseButtonStatus.length) {
                getInstance().mouseButtonStatus[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < getInstance().mouseButtonStatus.length) {
                getInstance().mouseButtonStatus[button] = false;
            }
        }
    }

    public static float getX() {
        return (float)getInstance().xPos;
    }
    public static float getY() {
        return (float)getInstance().yPos;
    }

    public static boolean mouseButtonStatus(int button) {
        if (button < getInstance().mouseButtonStatus.length) {
            return getInstance().mouseButtonStatus[button];
        } else {
            return false;
        }
    }
}
