package view.gui;

import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.IDrawStrategy;
import model.interfaces.IShape;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

import static model.ShapeShadingType.OUTLINE;

public class SelectedShapeOutline implements IShape {
    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;
    IDrawStrategy drawStrategy;
    ApplicationState appState;
    ShapeType shapeType;
    Color primary_color;
    Color secondary_color;
    ShapeShadingType current_shading_type;

    private IShape baseShape;

    public SelectedShapeOutline(IShape s){
        this.baseShape = s;
        this.startPoint = new MyPoint(s.getStartPoint().getX() -5  ,s.getStartPoint().getY() - 5);
        this.endPoint = new MyPoint(s.getEndPoint().getX()+4, s.getEndPoint().getY() + 5);
        this.paintCanvas = s.getPaintCanvas();
        this.graphics2d = paintCanvas.getGraphics2D();
        this.appState = s.getAppState();
        this.shapeType = s.getShapeType();
        this.current_shading_type = OUTLINE;
        this.primary_color = Color.BLACK;
        this.secondary_color = Color.BLACK;
        this.strategyDecider();
    }

    @Override
    public void draw() {
        drawStrategy.draw();
    }

    @Override
    public void run() {
        this.draw();
        baseShape.draw();
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

    public void strategyDecider(){
        switch(shapeType){
            case RECTANGLE:
                drawStrategy = new DrawRectangleStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color, true);
                break;
            case ELLIPSE:
                drawStrategy = new DrawEllipseStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color, true);
                break;
            case TRIANGLE:
                drawStrategy  = new DrawTriangleStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color, true);
                break;
        }
    }
}
