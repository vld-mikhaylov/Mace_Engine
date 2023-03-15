package GUI.Scene;

import Render.*;

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
