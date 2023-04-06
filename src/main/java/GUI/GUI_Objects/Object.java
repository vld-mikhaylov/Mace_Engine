/*
 ** ----------------------------------------------Object-------------------------------------------------------
 ** This is abstract class to create new gui_object classes, without any incapsulation problems.
 ** -----------------------------------------------------------------------------------------------------------
 ** MatrixObject - stores and renders changes in matrix.
 */

package GUI.GUI_Objects;

public abstract class Object {
    public Object() {}
    public abstract void init();

    public abstract void run();

    public abstract void update();
}