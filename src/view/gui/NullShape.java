package view.gui;

import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.IShape;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;

public class NullShape implements IShape {

    public NullShape(){ }
    @Override
    public void draw() {

    }

    @Override
    public void run() throws IOException {

    }

    @Override
    public MyPoint getEndPoint() {
        return null;
    }

    @Override
    public MyPoint getStartPoint() {
        return null;
    }

    @Override
    public ApplicationState getAppState() {
        return null;
    }

    @Override
    public ShapeType getShapeType() {
        return null;
    }

    @Override
    public ShapeShadingType getCurrent_shading_type() {
        return null;
    }

    @Override
    public Color getPrimary_color() {
        return null;
    }

    @Override
    public Color getSecondary_color() {
        return null;
    }

    @Override
    public PaintCanvasBase getPaintCanvas() {
        return null;
    }

    @Override
    public void delete() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
