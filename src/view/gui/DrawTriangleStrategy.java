package view.gui;

import model.ShapeColor;
import model.ShapeShadingType;
import model.interfaces.IDrawStrategy;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class DrawTriangleStrategy implements IDrawStrategy {
    MyPoint startPoint;
    MyPoint endPoint;
    PaintCanvasBase paintCanvas;
    Graphics2D graphics2d;
    ApplicationState appState;
    Color primary_color;
    Color secondary_color;
    ShapeShadingType current_shading_type;
    Polygon polygon;
    int[] xValues = new int[3];
    int[] yValues = new int[3];
    Boolean isOutline;


    DrawTriangleStrategy(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState, ShapeShadingType current_shading_type, Color primary_color, Color secondary_color, Boolean isOutline){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.appState = appState;
        this.current_shading_type = current_shading_type;
        this.primary_color = primary_color;
        this.secondary_color = secondary_color;
        this.xValues[0] = startPoint.getX();
        this.xValues[1] = endPoint.getX();
        this.xValues[2] = startPoint.getX();
        this.yValues[0] = startPoint.getY();
        this.yValues[1] = endPoint.getY();
        this.yValues[2] = endPoint.getY();
        polygon = new Polygon(xValues,yValues,3);
        this.isOutline = isOutline;
    }

    public void draw(){
        graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(primary_color);
        graphics2d.setStroke(new BasicStroke(5));
        if (isOutline){
            graphics2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        }
        switch (current_shading_type){
            case OUTLINE:
                graphics2d.drawPolygon(polygon);
                break;
            case FILLED_IN:
                graphics2d.fillPolygon(xValues,yValues,3);
                break;
            case OUTLINE_AND_FILLED_IN:
                graphics2d.fillPolygon(xValues,yValues,3);
                graphics2d.setColor(secondary_color);
                graphics2d.drawPolygon(xValues,yValues,3);
                break;
        }
    }
}
