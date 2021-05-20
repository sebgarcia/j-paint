package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoveCommand implements ICommand, IUndoable {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    Graphics2D graphics2d;
    List<Shape> oldShapeList = new ArrayList<Shape>();
    List<Shape> movedShapeList = new ArrayList<Shape>();
    List<Shape> tempSelectedShapesList = new ArrayList<Shape>();
    ApplicationState appState;
    int xOffset;
    int yOffset;

    public MoveCommand(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.graphics2d = paintCanvas.getGraphics2D();
        this.appState = appState;
        xOffset = endPoint.getX() - startPoint.getX();
        yOffset = endPoint.getY() - startPoint.getY();
    }
    @Override
    public void run() throws IOException {
        moveSelectedShapes();
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        for (Shape s: movedShapeList){
            //get rid of moved shapes
            clearCanvas();
            ShapesList.remove(s);
            SelectedShapeList.remove(s);
        }

        for (Shape s: oldShapeList){
            ShapesList.add(s);
            SelectedShapeList.add(s);
            SelectedShapeOutline border = new SelectedShapeOutline(s);
            border.draw();
        }

        for (Shape s: ShapesList.getShapesList()){
            s.draw();
        }
    }

    @Override
    public void redo() {
        for (Shape s: oldShapeList){
            clearCanvas();
            ShapesList.remove(s);
            SelectedShapeList.remove(s);
        }

        for (Shape s: movedShapeList){
            ShapesList.add(s);
            SelectedShapeList.add(s);
        }

        for (Shape s: ShapesList.getShapesList()){
            s.draw();
        }

    }

    public void coverShape(Shape oldShape){
        Shape coverShape = new Shape
                (paintCanvas,
                        oldShape.getStartPoint(),
                        oldShape.getEndPoint(),
                        oldShape.getAppState(),
                        oldShape.getShapeType(),
                        oldShape.getCurrent_shading_type(),
                        Color.WHITE,
                        Color.WHITE);
        coverShape.strategyDecider();
        coverShape.draw();
    }

    public void moveSelectedShapes(){
        tempSelectedShapesList = SelectedShapeList.getSelectedShapeList();
        for (Shape oldShape: tempSelectedShapesList){
            oldShapeList.add(oldShape);
        }

        for (Shape oldShape : oldShapeList){
            //create a list of new shapes with the edits using the old selected shapes as a starting point with the edit
            MyPoint newStartPoint = new MyPoint(oldShape.getStartPoint().getX() + xOffset, oldShape.getStartPoint().getY() + yOffset);
            MyPoint newEndPoint = new MyPoint(oldShape.getEndPoint().getX()+xOffset, oldShape.getEndPoint().y+yOffset);
            Shape newShape = new Shape
                                (paintCanvas,
                                newStartPoint,
                                newEndPoint,
                                oldShape.getAppState(),
                                oldShape.getShapeType(),
                                oldShape.getCurrent_shading_type(),
                                oldShape.getPrimary_color(),
                                oldShape.getSecondary_color());
            movedShapeList.add(newShape);
            newShape.strategyDecider();
            coverShape(oldShape);
            ShapesList.remove(oldShape);
            ShapesList.add(newShape);
        }

        SelectedShapeList.clear();
        clearCanvas();

        for (Shape s : ShapesList.getShapesList()){
            s.draw();
        }

        for (Shape movedShape: movedShapeList){
            SelectedShapeList.add(movedShape);
            IShape border = new SelectedShapeOutline(movedShape);
            border.draw();
        }
    }

    public void clearCanvas(){
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0,0, paintCanvas.getWidth(), paintCanvas.getHeight());
    }
}
