package Util;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    private static KeyListener keyInstance;
    private boolean keyPressed[] = new boolean[GLFW_KEY_LAST];


    private KeyListener() {}
    public static KeyListener getInstance() {
        if (KeyListener.keyInstance == null) {
            KeyListener.keyInstance = new KeyListener();
        }

        return KeyListener.keyInstance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            getInstance().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            getInstance().keyPressed[key] = false;
        }
    }
    public static boolean isKeyPressed(int keyCode) {
        return getInstance().keyPressed[keyCode];
    }
}