package view.gui;

import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.ICommand;
import model.interfaces.IDrawStrategy;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.awt.*;

public class Shape implements ICommand, IUndoable, IShape {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;
    List<Shape> tempShapesList = new ArrayList<Shape>();
    IDrawStrategy drawStrategy;
    ApplicationState appState;
    ShapeType shapeType;
    Color primary_color;
    Color secondary_color;
    ShapeShadingType current_shading_type;

    public Shape(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState, ShapeType shapeType, ShapeShadingType current_shading_type, Color primary_color, Color secondary_color ){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.graphics2d = paintCanvas.getGraphics2D();
        this.appState = appState;
        this.shapeType = shapeType;
        this.current_shading_type = current_shading_type;
        this.primary_color = primary_color;
        this.secondary_color = secondary_color;
    }

    public void run(){
        this.strategyDecider();
        drawStrategy.draw();
        CommandHistory.add(this);
        ShapesList.add(this);

    }

    public void draw(){
        drawStrategy.draw();
    }

    public void strategyDecider(){
        switch(shapeType){
            case RECTANGLE:
                drawStrategy = new DrawRectangleStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color);
                break;
            case ELLIPSE:
                drawStrategy = new DrawEllipseStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color);
                break;
            case TRIANGLE:
                drawStrategy  = new DrawTriangleStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color);
                break;
        }
    }

    public void undo(){
        // get the command structure
        //get the Shape List and re-draw all shapes, minus the most recent one
        this.delete();
        tempShapesList = ShapesList.getShapesList();
        for (Shape s: tempShapesList){
            s.draw();
        }
    }

    public void redo(){
        drawStrategy.draw();
        ShapesList.add(this);
    }

    public MyPoint getStartPoint(){
        return this.startPoint;
    }

    public MyPoint getEndPoint(){
        return this.endPoint;
    }


    public void delete(){
        ShapesList.remove(this);
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        graphics2d.setStroke(new BasicStroke(15));
        graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
    }

    public ApplicationState getAppState() {
        return appState;
    }

    public Color getPrimary_color() {
        return primary_color;
    }

    public Color getSecondary_color() {
        return secondary_color;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public ShapeShadingType getCurrent_shading_type() {
        return current_shading_type;
    }
}