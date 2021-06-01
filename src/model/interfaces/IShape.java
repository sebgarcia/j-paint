package model.interfaces;

import model.ShapeShadingType;
import model.ShapeType;
import model.persistence.ApplicationState;
import view.gui.MyPoint;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;

public interface IShape{
    void draw();
    void run() throws IOException;

    MyPoint getEndPoint();

    MyPoint getStartPoint();

    ApplicationState getAppState();

    ShapeType getShapeType();

    ShapeShadingType getCurrent_shading_type();

    Color getPrimary_color();

    Color getSecondary_color();

    PaintCanvasBase getPaintCanvas();

    void delete();

    void undo();

    void redo();
}
