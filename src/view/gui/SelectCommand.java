package view.gui;

import model.ShapeType;
import model.interfaces.ICommand;
import model.interfaces.IDrawStrategy;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.sql.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.*;

import java.io.IOException;

public class SelectCommand implements ICommand {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;
    List<IShape> tempShapesList = new ArrayList<IShape>();
    ApplicationState appState;

    public SelectCommand(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.graphics2d = paintCanvas.getGraphics2D();
        this.appState = appState;
    }

    public void run() throws IOException {
        SelectedShapeList.clear();
        clearCanvas();
        tempShapesList = ShapesList.getShapesList();
        for(IShape s : tempShapesList){
            s.draw();
            if (collision_detector(s,startPoint,endPoint)){
                SelectedShapeList.add(s);
            }
        }

        for(IShape s: SelectedShapeList.getSelectedShapeList()){
            IShape outline = new SelectedShapeOutline(s);
            outline.draw();
        }
    }

    public boolean collision_detector(IShape shape, MyPoint startPoint, MyPoint endPoint){
        //collision algorithm found in this stack overflow page
        //https://stackoverflow.com/questions/31022269/collision-detection-between-two-rectangles-in-java

        MyPoint shapeEndPoint = shape.getEndPoint();
        MyPoint shapeStartPoint = shape.getStartPoint();
        if (startPoint.getX() < shapeEndPoint.getX() && endPoint.getX() > shapeStartPoint.getX()
            && startPoint.getY() < shapeEndPoint.getY() && endPoint.getY() > shapeStartPoint.getY()){
            return true;
        }
        return false;
    }

    public void clearCanvas(){
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0,0, paintCanvas.getWidth(), paintCanvas.getHeight());
    }
}
