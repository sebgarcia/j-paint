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
            PaintCanvasBase paintCanvas = oldShape.getPaintCanvas();
            Shape cover;
            cover = new Shape
                    (paintCanvas,
                            oldShape.getStartPoint(),
                            oldShape.getEndPoint(),
                            oldShape.getAppState(),
                            oldShape.getShapeType(),
                            oldShape.getCurrent_shading_type(),
                            Color.WHITE,
                            Color.WHITE);
            cover.strategyDecider();
            cover.draw();
            ShapesList.remove(oldShape);
        }

        for (Shape s : ShapesList.getShapesList()){
            s.draw();
        }

        SelectedShapeList.clear();

        //MyPoint newStartPoint = new MyPoint(oldShape.getStartPoint().getX(), oldShape.getStartPoint().getY());
        //MyPoint newEndPoint = new MyPoint(oldShape.getEndPoint().getX(), oldShape.getEndPoint().getY());
    }
}
