package view.gui;

import model.ShapeShadingType;
import model.ShapeType;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class ShapeBuilder {
    private PaintCanvasBase paintCanvas;
    private MyPoint startPoint;
    private MyPoint endPoint;
    private Color primaryColor;
    private Color secondaryColor;
    private ApplicationState appState;
    private ShapeType shapeType;
    private ShapeShadingType shapeShadingType;

    public ShapeBuilder setPaintCanvas(PaintCanvasBase paintCanvas) {
        this.paintCanvas = paintCanvas;
        return this;
    }

    public ShapeBuilder setStartPoint(MyPoint p){
        this.startPoint = p;
        return this;
    }

    public ShapeBuilder setEndPoint(MyPoint p){
        this.endPoint = p;
        return this;
    }

    public ShapeBuilder setPrimaryColor(Color c){
        this.primaryColor = c;
        return this;
    }

    public ShapeBuilder setSecondaryColor(Color c){
        this.secondaryColor = c;
        return this;
    }

    public ShapeBuilder setApplicationState(ApplicationState appState){
        this.appState = appState;
        return this;
    }


    public ShapeBuilder setShapeType(ShapeType st) {
        this.shapeType = st;
        return this;
    }

    public ShapeBuilder setShapeShadingType(ShapeShadingType sst){
        this.shapeShadingType = sst;
        return this;
    }

    public Shape toShape(){
        return new Shape(paintCanvas, startPoint, endPoint, appState, shapeType, shapeShadingType, primaryColor, secondaryColor);
    }
}
