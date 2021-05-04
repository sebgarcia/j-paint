package view.gui;

import model.ShapeColor;
import model.ShapeShadingType;
import model.interfaces.IDrawStrategy;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class DrawEllipseStrategy implements IDrawStrategy {
    MyPoint startPoint;
    MyPoint endPoint;
    PaintCanvasBase paintCanvas;
    Graphics2D graphics2d;
    ApplicationState appState;
    Color primary_color;
    Color secondary_color;
    ShapeShadingType current_shading_type;

    DrawEllipseStrategy(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.appState = appState;
        primary_color = Color.BLUE;
        secondary_color = Color.GREEN;
        current_shading_type = appState.getActiveShapeShadingType();
    }
    public void draw(){
        graphics2d = paintCanvas.getGraphics2D();
        ShapeColor current_color = appState.getActivePrimaryColor();
        graphics2d.setColor(primary_color);
        graphics2d.setStroke(new BasicStroke(5));
        switch(current_shading_type){
            case OUTLINE:
                graphics2d.drawOval(startPoint.x, startPoint.y, (endPoint.x-startPoint.x),(endPoint.y- startPoint.y));
                break;
            case FILLED_IN:
                graphics2d.fillOval(startPoint.x, startPoint.y, (endPoint.x-startPoint.x),(endPoint.y- startPoint.y));
                break;
            case OUTLINE_AND_FILLED_IN:
                graphics2d.fillOval(startPoint.x, startPoint.y, (endPoint.x-startPoint.x),(endPoint.y- startPoint.y));
                graphics2d.setColor(secondary_color);
                graphics2d.drawOval(startPoint.x, startPoint.y, (endPoint.x-startPoint.x),(endPoint.y- startPoint.y));
                break;
        }
        //graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
    }
}
