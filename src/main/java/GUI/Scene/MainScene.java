package GUI.Scene;

import Render.*;

public class MainScene extends Scene {
    private RenderMatrix renderMatrix;

    public MainScene() {}

    public void init() {
        renderMatrix = new RenderMatrix();
        renderMatrix.init();
    }
    public void run() {
        renderMatrix.run();
    }
    public void update() {
        renderMatrix.update();
    }
}