package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements ICommand, IUndoable {
    List<Shape> deletedShapesList = new ArrayList<Shape>();
    List<Shape> tempSelectedShapesList = new ArrayList<Shape>();
    PaintCanvasBase paintCanvasBase;
    Graphics2D graphics2D;

    public DeleteCommand(PaintCanvasBase p){
        this.paintCanvasBase = p;
    }

    @Override
    public void run() throws IOException {
        coverShape();
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        for (Shape s: deletedShapesList){
            SelectedShapeList.add(s);
            s.draw();
        }
    }

    @Override
    public void redo() {
        coverShape();
    }


    public void coverShape(){
        tempSelectedShapesList = SelectedShapeList.getSelectedShapeList();
        for (Shape oldShape : tempSelectedShapesList){
            deletedShapesList.add(oldShape);
        }
        for (Shape oldShape : deletedShapesList){
            ShapesList.remove(oldShape);
        }

        clearCanvas();
        SelectedShapeList.clear();
        
        for (Shape s : ShapesList.getShapesList()){
            s.draw();
        }


        //MyPoint newStartPoint = new MyPoint(oldShape.getStartPoint().getX(), oldShape.getStartPoint().getY());
        //MyPoint newEndPoint = new MyPoint(oldShape.getEndPoint().getX(), oldShape.getEndPoint().getY());
    }
    public void clearCanvas(){
        graphics2D = paintCanvasBase.getGraphics2D();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0, paintCanvasBase.getWidth(), paintCanvasBase.getHeight());
    }
}
