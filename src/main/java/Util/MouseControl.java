package Util;

import Config.ConfigValues;
import GUI.GUI_Objects.MatrixObject.Matrix.Matrix;
import GUI.Window;

import static org.lwjgl.glfw.GLFW.*;

public class MouseControl {
    /** Variable position of the mouse along the X coordinate.*/
    private float xPos;
    /** Variable position of the mouse along the Y coordinate.*/
    private float yPos;
    /** An array of mouse position variables transformed into matrix cell format.*/
    private int[] m_Pos;

    /** An instance of the MouseListener class, which is necessary to read the position of the mouse and the state of its buttons.*/
    private MouseListener mouseListenerInstance;

    public MouseControl() {
        m_Pos = new int[2];

        mouseListenerInstance = new MouseListener();
    }
    public void init() {
        glfwSetCursorPosCallback(Window.window, mouseListenerInstance::positionCallback);
        glfwSetMouseButtonCallback(Window.window, mouseListenerInstance::buttonCallback);
    }

    /** Depending on the position of the mouse on the screen, it sends data to the necessary GUI object.*/
    public void run() {
        xPos = mouseListenerInstance.getX();
        yPos = mouseListenerInstance.getY();

        boolean xPos_InMatrix = (xPos >= ConfigValues.matrixWidth[0]) && (xPos < ConfigValues.matrixWidth[1]);
        boolean yPos_InMatrix = (yPos >= ConfigValues.matrixHeight[0]) && (yPos < ConfigValues.matrixHeight[1]);
        if (mouseListenerInstance.buttonStatus(0) && xPos_InMatrix && yPos_InMatrix) {
            m_Pos[0] = (int) (((xPos - ConfigValues.matrixWidth[0]) - ((xPos - ConfigValues.matrixWidth[0]) % ConfigValues.spriteSize)) / ConfigValues.spriteSize);
            m_Pos[1] = (int) (((yPos - ConfigValues.matrixHeight[0]) - ((yPos - ConfigValues.matrixHeight[0]) % ConfigValues.spriteSize)) / ConfigValues.spriteSize);
            Matrix.inputData.add(m_Pos[0]);
            Matrix.inputData.add(m_Pos[1]);
            Matrix.inputData.add(KeyboardControl.currentMaterial);
        }
    }
}

class MouseListener {
    private boolean[] mouseButtonStatus;
    private double xPos, yPos;

    public MouseListener() {
        mouseButtonStatus =  new boolean[GLFW_MOUSE_BUTTON_LAST];

        xPos = 0.0;
        yPos = 0.0;
    }

    public void positionCallback(long window, double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public void buttonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < mouseButtonStatus.length) {
                mouseButtonStatus[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < mouseButtonStatus.length) {
                mouseButtonStatus[button] = false;
            }
        }
    }

    public float getX() {
        return (float) xPos;
    }
    public float getY() {
        return (float) yPos;
    }

    public boolean buttonStatus(int button) {
        if (button < mouseButtonStatus.length) {
            return mouseButtonStatus[button];
        } else {
            return false;
        }
    }
}