package GUI.GUI_Objects.MatrixObject;

import GUI.GUI_Objects.MatrixObject.Matrix.Matrix;
import GUI.GUI_Objects.MatrixObject.Physics.MatrixPhysic;
import GUI.GUI_Objects.Object;
import GUI.GUI_Objects.ThreadManager;

public class MatrixObject extends Object {
    /** An instance of the Matrix class that sends the matrix data for rendering, accepts changes from input and physics.*/
    private Matrix matrixInstance;
    /** An instance of the MatrixPhysic class that receives data from each sprite, processes its movement, and writes these changes to the matrix.*/
    private MatrixPhysic matrixPhysicInstance;

    public MatrixObject() {
        matrixInstance = new Matrix();
        matrixPhysicInstance = new MatrixPhysic();

        matrixInstance.init();
        matrixPhysicInstance.init();
    }

    /** While the thread is running, the matrix will accept data from the user and other classes, and then send it to render.*/
    @Override
    public void run() {
        while (ThreadManager.isThreadAlive[0]) {
            matrixInstance.run();
            matrixPhysicInstance.run();
        }
    }
}