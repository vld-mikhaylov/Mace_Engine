package GUI.Scene;

import Core.*;
import Render.*;

import org.lwjgl.*;
import java.nio.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class MainScene extends Scene {
    private Render render;
    public MainScene() {
        init();
    }
    public void init() {
        render = new Render();
    }
    public void update(float dt) {
        render.update();
    }
}
