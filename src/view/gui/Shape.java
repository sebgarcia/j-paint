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
import java.lang.*;

public class Shape implements ICommand, IUndoable, IShape {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;
    List<IShape> tempShapesList = new ArrayList<IShape>();
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
        startEndPointAdjustor();
        this.strategyDecider();
    }

    public void run(){
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
                drawStrategy = new DrawRectangleStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color, false);
                break;
            case ELLIPSE:
                drawStrategy = new DrawEllipseStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color, false);
                break;
            case TRIANGLE:
                drawStrategy  = new DrawTriangleStrategy(paintCanvas,startPoint,endPoint, appState, current_shading_type, primary_color, secondary_color, false);
                break;
        }
    }

    public void undo(){
        // get the command structure
        //get the Shape List and re-draw all shapes, minus the most recent one
        this.delete();
        tempShapesList = ShapesList.getShapesList();
        for (IShape s: tempShapesList){
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
        SelectedShapeList.remove(this);
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        graphics2d.setStroke(new BasicStroke(15));
        graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
    }

    public void startEndPointAdjustor(){
        int adjusted_start_x = Math.min(startPoint.getX(),endPoint.getX());
        int adjusted_start_y = Math.min(startPoint.getY(),endPoint.getY());
        int adjusted_end_x = Math.max(startPoint.getX(),endPoint.getX());
        int adjusted_end_y = Math.max(startPoint.getY(),endPoint.getY());

        this.startPoint = new MyPoint(adjusted_start_x,adjusted_start_y);
        this.endPoint = new MyPoint(adjusted_end_x,adjusted_end_y);
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

    public PaintCanvasBase getPaintCanvas(){
        return paintCanvas;
    }
}
