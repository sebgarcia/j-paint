package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class DrawCommand implements ICommand, IUndoable {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;


    public DrawCommand(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.graphics2d = paintCanvas.getGraphics2D();
    }

    public void run(){
        graphics2d.setColor(Color.BLUE);
        graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        CommandHistory.add(this);
    }

    public void undo(){
        graphics2d.setColor(Color.WHITE);
        graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
    }

    public void redo(){
        graphics2d.setColor(Color.BLUE);
        graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
    }

}
