package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasteCommand implements ICommand, IUndoable {

    List<Shape> pastedShapes = new ArrayList<>();

    @Override
    public void run() throws IOException {
        System.out.println("running paste command");
        for (Shape s : Clipboard.getClipboard()){
            PasteShape(s);
        }

        for(Shape s : ShapesList.getShapesList()){
            s.draw();
        }
        System.out.println(ShapesList.getShapesList());
        CommandHistory.add(this);
    }


    @Override
    public void undo() {
        for (Shape s : pastedShapes){
            s.delete();
            ShapesList.remove(s);
        }
        for(Shape s : ShapesList.getShapesList()){
            s.draw();
        }
    }

    @Override
    public void redo() {
        for (Shape s : Clipboard.getClipboard()){
            PasteShape(s);
        }

        for(Shape s : ShapesList.getShapesList()){
            s.draw();
        }
    }

    public void PasteShape(Shape oldShape){
        MyPoint newStartPoint = new MyPoint(oldShape.getStartPoint().getX() + 100, oldShape.getStartPoint().getY() + 100);
        MyPoint newEndPoint = new MyPoint(oldShape.getEndPoint().getX()+100, oldShape.getEndPoint().y+100);
        PaintCanvasBase paintCanvas = oldShape.getPaintCanvas();
        Shape newShape;
        newShape = new Shape
                                    (paintCanvas,
                                    newStartPoint,
                                    newEndPoint,
                                    oldShape.getAppState(),
                                    oldShape.getShapeType(),
                                    oldShape.getCurrent_shading_type(),
                                    oldShape.getPrimary_color(),
                                    oldShape.getSecondary_color());
        newShape.strategyDecider();
        ShapesList.add(newShape);
        pastedShapes.add(newShape);
    }
}
