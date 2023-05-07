/*
 ** ------------------------------------------MatrixObject----------------------------------------------------
 ** This class stores, refreshes and renders game matrix.
 ** -----------------------------------------------------------------------------------------------------------
 ** init() - initialise Matrix class and it's render class.
 ** run() - updates changes in matrix, send them to RenderMatrix and after that render image.
 ** update() - updates RenderMatrix vertexArray if there any changes in the matrix.
 ** -----------------------------------------------------------------------------------------------------------
 ** OTHER CLASS USAGE: Matrix, RenderMatrix.
 ** PATTERN: None.
 */


package GUI.GUI_Objects.MatrixObject;

import GUI.GUI_Objects.MatrixObject.Matrix.*;
import GUI.GUI_Objects.MatrixObject.Physics.MatrixBypass;
import GUI.GUI_Objects.MatrixObject.Render.*;
import GUI.GUI_Objects.Object;

public class MatrixObject extends Object {
    /** Matrix instance for MatrixObject class.*/
    private Matrix matrixInstance;
    /** RenderMatrix instance for MatrixObject class.*/
    private RenderMatrix renderInstance;

    MatrixBypass bypassInstance;

    public MatrixObject() {
        matrixInstance = new Matrix();
        renderInstance = new RenderMatrix();
        bypassInstance = new MatrixBypass();
    }
    /** Initialise Matrix class and it's render class.*/
    public void init() {
        matrixInstance.init();
        renderInstance.init();
    }

    /** Updates changes in matrix, send them to RenderMatrix and after that render image.*/
    public void run() {
        update();
        renderInstance.render();
    }

    /** Updates RenderMatrix vertexArray if there any changes in the matrix.*/
    public void update() {
        bypassInstance.run();
        if (matrixInstance.update()) {
            renderInstance.update();
        }
    }
}