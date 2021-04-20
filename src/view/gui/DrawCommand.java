package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;
import java.util.Stack;
import java.awt.*;

public class DrawCommand implements ICommand, IUndoable {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;
    Stack<IUndoable> undoableStack;


    public DrawCommand(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.graphics2d = paintCanvas.getGraphics2D();
    }

    public void run(){
        graphics2d.setColor(Color.BLUE);
        graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        CommandHistory.add(this);
    }

    public void undo(){
        // get the command structure
        //get the command history and re-draw all shapes, minus the most recent one
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        undoableStack = CommandHistory.getUndoStack(); //get the command history and re-draw all shapes, minus the most recent one
        for(int i = 0; i < (undoableStack.size()); i+=1){
            IUndoable c = undoableStack.get(i);
            c.rerun();
        }
    }

    public void redo(){
        graphics2d.setColor(Color.BLUE);
        graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
    }

    public void rerun(){
        graphics2d.setColor(Color.BLUE);
        graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
    }

}
