package GUI.GUI_Objects.MatrixObject.Physics;

import GUI.GUI_Objects.MatrixObject.Physics.Gas.*;
import GUI.GUI_Objects.MatrixObject.Physics.Liquid.*;
import GUI.GUI_Objects.MatrixObject.Physics.Solid.*;

public class MaterialRecord {
    public static Material getInstance(int material_ID) {
        Material materialInstance = null;

        switch(material_ID) {
            case 1:
                materialInstance = new Void_Gas_1();
                break;
            case 2:
                materialInstance = new Void_Liquid_2();
                break;
            case 3:
                materialInstance = new Void_Solid_3();
                break;
            case 12:
                materialInstance = new Water_Liquid_12();
                break;
        }
        return materialInstance;
    }
}