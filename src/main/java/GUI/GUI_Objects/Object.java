/*
 ** ----------------------------------------------Object-------------------------------------------------------
 ** This is abstract class to create new gui_object classes, without any incapsulation problems.
 ** -----------------------------------------------------------------------------------------------------------
 ** MatrixObject - stores and renders changes in matrix.
 */

package GUI.GUI_Objects;

public abstract class Object implements Runnable {
    public Object() {}

    @Override
    public abstract void run();
}