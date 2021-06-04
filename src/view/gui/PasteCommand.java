package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasteCommand implements ICommand, IUndoable {

    List<IShape> pastedShapes = new ArrayList<>();

    @Override
    public void run() throws IOException {
        for (IShape s : Clipboard.getClipboard()){
            PasteShape(s);
        }

        for(IShape s : ShapesList.getShapesList()){
            s.draw();
        }
        CommandHistory.add(this);
    }


    @Override
    public void undo() {
        for (IShape s : pastedShapes){
            s.delete();
            ShapesList.remove(s);
        }
        for(IShape s : ShapesList.getShapesList()){
            s.draw();
        }
    }

    @Override
    public void redo() {
        for (IShape s : Clipboard.getClipboard()){
            PasteShape(s);
        }

        for(IShape s : ShapesList.getShapesList()){
            s.draw();
        }
    }

    public void PasteShape(IShape oldShape){

        if (oldShape instanceof Group){
            Group group = (Group) oldShape;
            group.ShapeGroup.forEach(this::pasteSingleShape);
        } else {
            pasteSingleShape(oldShape);
        }
    }

    private void pasteSingleShape(IShape oldShape) {
        MyPoint newStartPoint = new MyPoint(oldShape.getStartPoint().getX() + 100, oldShape.getStartPoint().getY() + 100);
        MyPoint newEndPoint = new MyPoint(oldShape.getEndPoint().getX() + 100, oldShape.getEndPoint().y + 100);
        PaintCanvasBase paintCanvas = oldShape.getPaintCanvas();
        IShape newShape;
        newShape = new Shape
                (paintCanvas,
                        newStartPoint,
                        newEndPoint,
                        oldShape.getAppState(),
                        oldShape.getShapeType(),
                        oldShape.getCurrent_shading_type(),
                        oldShape.getPrimary_color(),
                        oldShape.getSecondary_color());
        ShapesList.add(newShape);
        pastedShapes.add(newShape);
    }
}
