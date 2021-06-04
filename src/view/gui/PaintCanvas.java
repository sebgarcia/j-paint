package view.gui;

import view.interfaces.PaintCanvasBase;
import java.awt.*;


public class PaintCanvas extends PaintCanvasBase {

    public Graphics2D getGraphics2D() {
        return (Graphics2D)getGraphics();
    }
}

