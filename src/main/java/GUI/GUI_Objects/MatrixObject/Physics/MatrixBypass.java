package GUI.GUI_Objects.MatrixObject.Physics;

import GUI.GUI_Objects.MatrixObject.Matrix.Matrix;

import java.util.LinkedList;

public class MatrixBypass {
    /** Temporary X position counter of certain sprite on matrix.*/
    private int XPos_Counter;
    /** Temporary Y position counter of certain sprite on matrix.*/
    private int YPos_Counter;
    /** Instance of the Material class to proceed physics algorithm of certain sprite and gain matrix parameter changes.*/
    private Material materialInstance;
    /** Array of changes on (0) X and (1) Y position.*/
    private int[] posChange;

    public MatrixBypass() {
        XPos_Counter = 0;
        YPos_Counter = Matrix.matrixHeight - 1;
        Matrix.bypassData = new LinkedList<>();
    }

    /** Run physics algorithm of every sprite.*/
    public void run() {
        if (YPos_Counter >= 0) {
            if (XPos_Counter <= 99) {
                if (Matrix.materialMatrix[XPos_Counter][YPos_Counter][0] != 3) {
                    materialInstance = MaterialRecord.getInstance(Matrix.materialMatrix[XPos_Counter][YPos_Counter][0]);
                    posChange = materialInstance.getPosChange(readSurrounding(XPos_Counter, YPos_Counter));
                    if ((posChange[0] != 0 || posChange[1] != 0) && Matrix.materialMatrix[XPos_Counter][YPos_Counter][1] == 0) {
                        Matrix.bypassData.add(XPos_Counter + posChange[0]);
                        Matrix.bypassData.add(YPos_Counter + posChange[1]);
                        Matrix.bypassData.add(Matrix.materialMatrix[XPos_Counter][YPos_Counter][0]);
                        Matrix.bypassData.add(1);
                        Matrix.bypassData.add(XPos_Counter);
                        Matrix.bypassData.add(YPos_Counter);
                        Matrix.bypassData.add(3);
                        Matrix.bypassData.add(0);
                    }
                }
                XPos_Counter++;
            } else {
                XPos_Counter = 0;
                YPos_Counter--;
            }
        } else {
            YPos_Counter = Matrix.matrixHeight - 1;
            XPos_Counter = 0;
            int counter = 0;
            for (int y = Matrix.matrixHeight - 1; y != 0; y--) {
                for (int x = 0; x != 100; x++) {
                    if (Matrix.materialMatrix[x][y][0] != 3) {
                        counter++;
                    }
                    if (Matrix.materialMatrix[x][y][1] == 1) {
                        Matrix.bypassData.add(x);
                        Matrix.bypassData.add(y);
                        Matrix.bypassData.add(Matrix.materialMatrix[x][y][0]);
                        Matrix.bypassData.add(0);
                    }
                }
            }
            System.out.println("Amount of sprites: " + counter);
        }
    }

    /** Read from matrix surrounding sprites and create array 3x3 of them.*/
    private int[][] readSurrounding(int XPos, int YPos) {
        int[][] surrounding = new int[3][3];
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (XPos + x < 0) {
                    surrounding[x + 1][y + 1] = -1;
                } else if (XPos + x >= Matrix.matrixWidth) {
                    surrounding[x + 1][y + 1] = -1;
                } else if (YPos + y < 0) {
                    surrounding[x + 1][y + 1] = -1;
                } else if (YPos + y >= Matrix.matrixWidth) {
                    surrounding[x + 1][y + 1] = -1;
                } else {
                    surrounding[x + 1][y + 1] = Matrix.materialMatrix[XPos + x][YPos + y][0];
                }
            }
        }
        return surrounding;
    }
}
