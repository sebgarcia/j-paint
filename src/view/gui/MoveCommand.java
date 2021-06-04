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
    List<IShape> oldShapeList = new ArrayList<>();
    List<IShape> movedShapeList = new ArrayList<>();
    List<IShape> tempSelectedShapesList = new ArrayList<>();
    List<IShape> tempMovedShapesList = new ArrayList<>();
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
        for (IShape s: movedShapeList){
            System.out.println(ShapesList.getShapesList());
            //get rid of moved shapes
            clearCanvas();
            ShapesList.remove(s);
            SelectedShapeList.remove(s);
        }

        for (IShape s: oldShapeList){
            ShapesList.add(s);
            SelectedShapeList.add(s);
            SelectedShapeOutline border = new SelectedShapeOutline(s);
            border.draw();
        }

        for (IShape s: ShapesList.getShapesList()){
            s.draw();
        }
    }

    @Override
    public void redo() {
        for (IShape s: oldShapeList){
            clearCanvas();
            ShapesList.remove(s);
            SelectedShapeList.remove(s);
        }

        for (IShape s: movedShapeList){
            ShapesList.add(s);
            SelectedShapeList.add(s);
        }

        for (IShape s: ShapesList.getShapesList()){
            s.draw();
        }

    }

    public void moveSelectedShapes() throws IOException {
        System.out.println(ShapesList.getShapesList());
        tempSelectedShapesList = SelectedShapeList.getSelectedShapeList();
        for (IShape oldShape: tempSelectedShapesList){
            oldShapeList.add(oldShape);
        }

        for (IShape oldShape : oldShapeList) {
            if (oldShape instanceof Group) {
                Group group = (Group) oldShape;
                for(IShape s: group.ShapeGroup){
                    tempMovedShapesList.add(moveSingleShape(s));
                }

                IShape newGroup = new Group(paintCanvas, tempMovedShapesList);
                newGroup.run();
                movedShapeList.add(newGroup);
            } else {
                IShape movedShape = moveSingleShape(oldShape);
                SelectedShapeList.add(movedShape);
                ShapesList.add(movedShape);
                movedShapeList.add(movedShape);
                }
            ShapesList.remove(oldShape);
            SelectedShapeList.remove(oldShape);
        }

        SelectedShapeList.clear();
        clearCanvas();

        for (IShape s : ShapesList.getShapesList()){
            s.draw();
        }

        for (IShape movedShape: movedShapeList){
            SelectedShapeList.add(movedShape);
            IShape border = new SelectedShapeOutline(movedShape);
            border.draw();

        }
    }

    public void clearCanvas(){
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0,0, paintCanvas.getWidth(), paintCanvas.getHeight());
    }

    public IShape moveSingleShape(IShape oldShape){
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
        clearCanvas();
        ShapesList.remove(oldShape);
        return newShape;
    }
}
