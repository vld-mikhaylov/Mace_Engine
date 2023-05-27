package GUI.GUI_Objects.MatrixObject.Physics;

import GUI.GUI_Objects.MatrixObject.Matrix.Matrix;
import Util.KeyboardControl;
import Util.TimeTool;

public class MatrixPhysic {
    /** Temporary X position counter of certain sprite on matrix.*/
    private int xPos;
    /** Temporary Y position counter of certain sprite on matrix.*/
    private int yPos;
    /** Array of position changes on (0) X and (1) Y coordinates.*/
    private int[] posChange;
    /** The variable is necessary for the timely development of the physical algorithm.*/
    private boolean isTime;
    /** Instance of the Material class to proceed physics algorithm of certain sprite and gain matrix parameter changes.*/
    private Material materialInstance;
    /** An instance of the TimeTool class needed to fragment time to perform certain operations.*/
    private TimeTool timeToolInstance;

    public MatrixPhysic() {
        timeToolInstance = new TimeTool();

        xPos = 0;
        yPos = Matrix.matrixHeight - 1;
    }
    public void init() {
        timeToolInstance.setTimer();
    }

    /** Run physics algorithm of every sprite.*/
    public void run() {
        if (timeToolInstance.getTimer(1/50F / KeyboardControl.currentSpeed)) {
            isTime = true;
            timeToolInstance.setTimer();
        }

        if (isTime) {
            if (yPos >= 0) {
                if (xPos <= Matrix.matrixWidth - 1) {
                    if (Matrix.matrix[xPos][yPos][0] != 3) {
                        materialInstance = MaterialRecord.getInstance(Matrix.matrix[xPos][yPos][0]);
                        posChange = materialInstance.getPosChange(readSurrounding(xPos, yPos));
                        if ((posChange[0] != 0 || posChange[1] != 0) && Matrix.matrix[xPos][yPos][1] == 0 && Matrix.matrix[xPos + posChange[0]][yPos + posChange[1]][1] == 0) {
                            int tempValues[] = new int[2];
                            tempValues[0] = Matrix.matrix[xPos + posChange[0]][yPos + posChange[1]][0];
                            tempValues[1] = Matrix.matrix[xPos + posChange[0]][yPos + posChange[1]][1];
                            Matrix.physicData.add(xPos + posChange[0]);
                            Matrix.physicData.add(yPos + posChange[1]);
                            Matrix.physicData.add(Matrix.matrix[xPos][yPos][0]);
                            Matrix.physicData.add(1);
                            Matrix.physicData.add(xPos);
                            Matrix.physicData.add(yPos);
                            Matrix.physicData.add(tempValues[0]);
                            Matrix.physicData.add(tempValues[1]);
                            xPos--;
                        }
                    }
                    xPos++;
                } else {
                    xPos = 0;
                    yPos--;
                }
            } else {
                yPos = Matrix.matrixHeight - 1;
                xPos = 0;
                for (int y = Matrix.matrixHeight - 1; y != 0; y--) {
                    for (int x = 0; x != Matrix.matrixWidth; x++) {
                        if (Matrix.matrix[x][y][1] == 1) {
                            Matrix.physicData.add(x);
                            Matrix.physicData.add(y);
                            Matrix.physicData.add(Matrix.matrix[x][y][0]);
                            Matrix.physicData.add(0);
                        }
                    }
                }
            }
        }

        if (yPos == Matrix.matrixHeight - 1 && xPos == 0) {
            isTime = false;
        }
    }

    /** Read from matrix surrounding sprites and create array 3x3 of them.*/
    private boolean[][] readSurrounding(int xPos, int yPos) {
        boolean[][] surrounding = new boolean[3][3];

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (xPos + x < 0) {
                    surrounding[x + 1][y + 1] = false;
                } else if (xPos + x >= Matrix.matrixWidth) {
                    surrounding[x + 1][y + 1] = false;
                } else if (yPos + y < 0) {
                    surrounding[x + 1][y + 1] = false;
                } else if (yPos + y >= Matrix.matrixWidth) {
                    surrounding[x + 1][y + 1] = false;
                } else if (MaterialRecord.getInstance(Matrix.matrix[xPos][yPos][0]).getDensity() > MaterialRecord.getInstance(Matrix.matrix[xPos + x][yPos + y][0]).getDensity()) {
                    surrounding[x + 1][y + 1] = true;
                } else {
                    surrounding[x + 1][y + 1] = false;
                }
            }
        }
        return surrounding;
    }
}
