package Util;

import GUI.GUI_Objects.MatrixObject.Physics.MaterialRecord;
import GUI.Window;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardControl {
    /** A variable that sets the selected sprite material.*/
    public volatile static int currentMaterial;
    /** A variable that sets the refresh rate of sprite physics.*/
    public volatile static float currentSpeed;

    /** An array, the first cell of which contains data about the leaf, the second cell contains data about substances, and the third cell contains the aggregate state.*/
    private int currentMaterialData[];

    /** An instance of the KeyboardListener class, which is needed to read the state of the keyboard buttons.*/
    private KeyboardListener keyboardListenerInstance;

    public KeyboardControl() {
        keyboardListenerInstance = new KeyboardListener();
        currentMaterialData = new int[3];
    }
    public void init() {
        glfwSetKeyCallback(Window.window, keyboardListenerInstance::keyCallback);

        currentMaterial = 3;
        currentSpeed = 1F;
    }

    /** Depending on the key combination, changes the selected material for input into the matrix, or changes the speed of physics.*/
    public void run() {
        int currentNumberKey = getNumberKey();
        int currentLetterKey = getLetterKey();
        int currentStateKey = getStateKey();

        if (isButtonPressedOnce(GLFW_KEY_LEFT_CONTROL)) {
            if (isButtonPressedOnce(GLFW_KEY_LEFT_SHIFT)) {
                if (isButtonPressedOnce(currentNumberKey)) {
                    keyboardListenerInstance.keyPressedCounter[GLFW_KEY_LEFT_CONTROL]++;
                    keyboardListenerInstance.keyPressedCounter[GLFW_KEY_LEFT_SHIFT]++;
                    keyboardListenerInstance.keyPressedCounter[currentNumberKey]++;
                    currentMaterialData[0] = currentNumberKey;
                }
            } else if (isButtonPressedOnce(currentNumberKey)) {
                if (isButtonPressedOnce(currentStateKey)) {
                    keyboardListenerInstance.keyPressedCounter[GLFW_KEY_LEFT_CONTROL]++;
                    keyboardListenerInstance.keyPressedCounter[currentNumberKey]++;
                    keyboardListenerInstance.keyPressedCounter[currentStateKey]++;
                    currentMaterialData[1] = currentNumberKey - 48;
                    switch(currentStateKey) {
                        case GLFW_KEY_G:
                            currentMaterialData[2] = 1;
                            break;
                        case GLFW_KEY_L:
                            currentMaterialData[2] = 2;
                            break;
                        case GLFW_KEY_S:
                            currentMaterialData[2] = 3;
                            break;
                    }
                    if (MaterialRecord.getInstance(currentMaterialData[0] * 100 + currentMaterialData[1] * 10 + currentMaterialData[2]) != null) {
                        currentMaterial = currentMaterialData[0] * 100 + currentMaterialData[1] * 10 + currentMaterialData[2];
                    }
                }
            }
        } else if (isButtonPressedOnce(GLFW_KEY_UP)) {
            keyboardListenerInstance.keyPressedCounter[GLFW_KEY_UP]++;
            if (currentSpeed < Math.pow(2, 3)) {
                currentSpeed *= 2F;
            }
            System.out.println(currentSpeed);
        } else if (isButtonPressedOnce(GLFW_KEY_DOWN)) {
            keyboardListenerInstance.keyPressedCounter[GLFW_KEY_DOWN]++;
            if (currentSpeed > Math.pow(2, -3)) {
                currentSpeed /= 2F;
            }
            System.out.println(currentSpeed);
        } else if (isButtonPressedOnce(GLFW_KEY_SPACE)) {
            keyboardListenerInstance.keyPressedCounter[GLFW_KEY_SPACE]++;
            if (currentSpeed == 0F) {
                currentSpeed = 1F;
            } else {
                currentSpeed = 0;
            }
            System.out.println(currentSpeed);
        }
    }

    /** Returns the number key which is pressed.*/
    private int getNumberKey() {
        for (int key = GLFW_KEY_0; key <= GLFW_KEY_9; key++) {
            if (keyboardListenerInstance.keyStatus(key)) {
                return key;
            }
        }
        return -1;
    }
    /** Returns the letter key which is pressed.*/
    private int getLetterKey() {
        for (int key = GLFW_KEY_A; key <= GLFW_KEY_Z; key++) {
            if (keyboardListenerInstance.keyStatus(key)) {
                return key;
            }
        }
        return -1;
    }
    /** Returns the aggregation key which is pressed.*/
    private int getStateKey() {
        if (keyboardListenerInstance.keyStatus(GLFW_KEY_G)) {
            return GLFW_KEY_G;
        } else if (keyboardListenerInstance.keyStatus(GLFW_KEY_L)) {
            return GLFW_KEY_L;
        } else if (keyboardListenerInstance.keyStatus(GLFW_KEY_S)) {
            return GLFW_KEY_S;
        }
        return -1;
    }

    /** Checks whether a certain key is pressed and whether it has been pressed before to execute the command once.*/
    private boolean isButtonPressedOnce(int key) {
        if (key != -1) {
            if (keyboardListenerInstance.keyStatus(key) && keyboardListenerInstance.keyPressedCounter[key] == 0) {
                return true;
            }
        }
        return false;
    }
}

class KeyboardListener {
    private boolean keyPressed[];
    public int keyPressedCounter[];

    public KeyboardListener() {
        keyPressed = new boolean[GLFW_KEY_LAST];
        keyPressedCounter = new int[GLFW_KEY_LAST];
    }

    public void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            keyPressedCounter[key] = 0;
            keyPressed[key] = false;
        }
    }

    public boolean keyStatus(int key) {
        return keyPressed[key];
    }
}