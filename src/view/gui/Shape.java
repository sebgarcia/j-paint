package view.gui;

import model.ShapeType;
import model.interfaces.ICommand;
import model.interfaces.IDrawStrategy;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;
import java.util.Stack;
import java.awt.*;

public class Shape implements ICommand, IUndoable, IShape {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;
    Stack<IShape> tempShapesList;
    IDrawStrategy drawStrategy;
    ApplicationState appState;
    ShapeType shapeType;

    public Shape(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.graphics2d = paintCanvas.getGraphics2D();
        this.appState = appState;
    }

    public void run(){
        shapeType = appState.getActiveShapeType();
        switch(shapeType){
            case RECTANGLE:
                drawStrategy = new DrawRectangleStrategy(paintCanvas,startPoint,endPoint, appState);
                break;
            case ELLIPSE:
                drawStrategy = new DrawEllipseStrategy(paintCanvas,startPoint,endPoint, appState);
            case TRIANGLE:
                drawStrategy  = new DrawTriangleStrategy(paintCanvas, startPoint, endPoint, appState);
        }

        System.out.println(shapeType);
        //drawStrategy = new DrawRectangleStrategy(paintCanvas,startPoint,endPoint, appState);
        drawStrategy.draw();
        CommandHistory.add(this);
        ShapesList.add(this);
    }

    public void draw(){
        drawStrategy.draw();
        //graphics2d.setColor(Color.BLUE);
        //graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
    }

    public void undo(){
        // get the command structure
        //get the Shape List and re-draw all shapes, minus the most recent one
        ShapesList.remove();
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        tempShapesList = ShapesList.getShapesList();
        for(int i = 0; i < (tempShapesList.size()); i+=1){
            IShape c = tempShapesList.get(i);
            c.draw();
        }
    }

    public void redo(){
        drawStrategy.draw();
        ShapesList.add(this);
    }


}
