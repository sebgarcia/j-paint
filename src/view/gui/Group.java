package view.gui;

import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group implements ICommand, IShape, IUndoable {

    List<IShape> ShapeGroup = new ArrayList<>();
    public MyPoint startPoint;
    public MyPoint endPoint;
    public PaintCanvasBase paintCanvasBase;
    public Graphics2D graphics2D;

    Group(PaintCanvasBase paintCanvasBase){
        this.paintCanvasBase = paintCanvasBase;
        this.graphics2D = paintCanvasBase.getGraphics2D();
    }

    @Override
    public void draw() {
        for (IShape s: ShapeGroup){
         s.draw();
        }
    }

    @Override
    public void run() throws IOException {
        for (IShape s: SelectedShapeList.getSelectedShapeList()){
            ShapeGroup.add(s);
            ShapesList.remove(s);
        }
        coordinateDecider();
        SelectedShapeList.clear();
        SelectedShapeList.add(this);
        ShapesList.add(this);
        clearCanvas();
        redrawSelectedShapes();
    }

    @Override
    public MyPoint getEndPoint() {
        return endPoint;
    }

    @Override
    public MyPoint getStartPoint() {
        return startPoint;
    }

    public void coordinateDecider(){
        List<Integer> startXList = new ArrayList<Integer>();
        List<Integer> startYList = new ArrayList<Integer>();
        List<Integer> endXList = new ArrayList<Integer>();
        List<Integer> endYList = new ArrayList<Integer>();

        ShapeGroup.forEach(s -> {
            startXList.add(s.getStartPoint().getX());
            startYList.add(s.getStartPoint().getY());
            endXList.add(s.getEndPoint().getX());
            endYList.add(s.getEndPoint().getY());
                }
        );

        int startX = Collections.min(startXList);
        int startY = Collections.min(startYList);
        int endX = Collections.max(endXList);
        int endY = Collections.max(endYList);

        this.startPoint = new MyPoint(startX,startY);
        this.endPoint = new MyPoint(endX,endY);
    }

    @Override
    public ApplicationState getAppState() {
        return null;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.RECTANGLE;
    }

    @Override
    public ShapeShadingType getCurrent_shading_type() {
        //System.out.println("blah");
        return null;
    }

    @Override
    public Color getPrimary_color() {
        return null;
    }

    @Override
    public Color getSecondary_color() {
        return null;
    }

    @Override
    public PaintCanvasBase getPaintCanvas() {
        return paintCanvasBase;
    }

    @Override
    public void delete() {

    }

    public void clearCanvas(){
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0, paintCanvasBase.getWidth(), paintCanvasBase.getHeight());
    }

    public void redrawSelectedShapes(){
        ShapesList.getShapesList().forEach(IShape::draw);
        SelectedShapeList.getSelectedShapeList().forEach(s-> {
            new SelectedShapeOutline(s).run();
        });
    }

    @Override
    public void undo() {
        SelectedShapeList.remove(this);
        ShapeGroup.forEach(s->{
            ShapesList.add(s);
            SelectedShapeList.add(s);
        });
        clearCanvas();
        redrawSelectedShapes();
    }

    @Override
    public void redo() {
        for(IShape s: ShapeGroup){
            ShapesList.remove(s);
            SelectedShapeList.remove(s);
        }
        ShapesList.add(this);
        SelectedShapeList.add(this);
        clearCanvas();
        redrawSelectedShapes();
    }
}
