package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;
import java.util.Stack;
import java.awt.*;

public class Rectangle implements ICommand, IUndoable, IShape {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;
    Stack<IShape> tempShapesList;

    public Rectangle(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.graphics2d = paintCanvas.getGraphics2D();
    }

    public void run(){
        this.draw();
        CommandHistory.add(this);
        ShapesList.add(this);
    }

    public void draw(){
        graphics2d.setColor(Color.BLUE);
        graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
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
        this.draw();
        ShapesList.add(this);
    }


}
