package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements ICommand, IUndoable {
    List<IShape> deletedShapesList = new ArrayList<IShape>();
    List<IShape> tempSelectedShapesList = new ArrayList<IShape>();
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
        for (IShape s: deletedShapesList){
            SelectedShapeList.add(s);
            ShapesList.add(s);
            s.draw();
        }
    }

    @Override
    public void redo() {
        coverShape();
    }


    public void coverShape(){
        tempSelectedShapesList = SelectedShapeList.getSelectedShapeList();
        for (IShape oldShape : tempSelectedShapesList){
            deletedShapesList.add(oldShape);
        }
        for (IShape oldShape : deletedShapesList){
            ShapesList.remove(oldShape);
        }

        clearCanvas();
        SelectedShapeList.clear();

        for (IShape s : ShapesList.getShapesList()){
            s.draw();
        }

    }
    public void clearCanvas(){
        graphics2D = paintCanvasBase.getGraphics2D();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0, paintCanvasBase.getWidth(), paintCanvasBase.getHeight());
    }
}
