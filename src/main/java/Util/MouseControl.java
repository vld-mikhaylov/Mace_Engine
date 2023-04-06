/*
 ** -------------------------------------------MouseControl----------------------------------------------------
 ** This class use MouseListener to return mouse callbacks in comfortable for the gui_objects formats.
 ** -----------------------------------------------------------------------------------------------------------
 ** init() - initialise glfw mouse callback via MouseListener. (Only position and buttons)
 ** run() - updates current mouse position and checks which gui_objects list to fill with new input data.
 ** update() - return position of the mouse on current window frame.
 ** -----------------------------------------------------------------------------------------------------------
 ** OTHER CLASS USAGE: ConfigValues, Temp_MatrixObject.
 ** PATTERN: None.
 ** NOTE: Create individual Temp classes for the new gui_objects classes, and leave callbacks via list there.
 */

package Util;

import Config.ConfigValues;
import Temp.Temp_MatrixObject;

import java.util.LinkedList;
import static org.lwjgl.glfw.GLFW.*;

public class MouseControl {
    /** Window frame id to set correctly input callback.*/
    private long window_id;
    /** Current mouse position on the frame.*/
    private float xPos, yPos;

    public MouseControl(long window_id) {
        Temp_MatrixObject.matrixInputData = new LinkedList<>();
        this.window_id = window_id;
    }
    /** Initialise glfw mouse callback via MouseListener. (Only position and buttons)*/
    public void init() {
        glfwSetCursorPosCallback(window_id, MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(window_id, MouseListener::mouseButtonCallback);
    }

    /** Updates current mouse position and checks which GUI_Objects list to fill with new input data.*/
    public void run() {
        update();

        boolean xPos_InMatrix = (xPos >= ConfigValues.matrixWidth[0]) && (xPos < ConfigValues.matrixWidth[1]);
        boolean yPos_InMatrix = (yPos >= ConfigValues.matrixHeight[0]) && (yPos < ConfigValues.matrixHeight[1]);
        if (MouseListener.mouseButtonStatus(0) && xPos_InMatrix && yPos_InMatrix) {
            int m_xPos = (int) (((xPos - ConfigValues.matrixWidth[0]) - ((xPos - ConfigValues.matrixWidth[0]) % ConfigValues.spriteSize)) / ConfigValues.spriteSize);
            int m_yPos = (int) (((yPos - ConfigValues.matrixHeight[0]) - ((yPos - ConfigValues.matrixHeight[0]) % ConfigValues.spriteSize)) / ConfigValues.spriteSize);
            Temp_MatrixObject.matrixInputData.add(m_xPos);
            Temp_MatrixObject.matrixInputData.add(m_yPos);
            Temp_MatrixObject.matrixInputData.add(1);
        }
    }

    /** Return position of the mouse on current window frame.*/
    private void update() {
        xPos = MouseListener.getX();
        yPos = MouseListener.getY();
    }
}
