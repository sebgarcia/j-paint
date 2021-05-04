package view.gui;

import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import model.interfaces.IDrawStrategy;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import java.awt.*;

public class DrawRectangleStrategy implements IDrawStrategy {
    MyPoint startPoint;
    MyPoint endPoint;
    PaintCanvasBase paintCanvas;
    Graphics2D graphics2d;
    ApplicationState appState;
    ShapeShadingType current_shading_type;
    Color primary_color;
    Color secondary_color;



    DrawRectangleStrategy(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.appState = appState;
        current_shading_type = appState.getActiveShapeShadingType();
        primary_color = Color.BLUE;
        secondary_color = Color.GREEN;
    }

    public void draw(){
    graphics2d = paintCanvas.getGraphics2D();
    ShapeColor current_color = appState.getActivePrimaryColor();
    //ShapeShadingType current_shading_type = appState.getActiveShapeShadingType();
    //System.out.println(current_color);
    graphics2d.setColor(primary_color);
    graphics2d.setStroke(new BasicStroke(5));
    switch(current_shading_type){
        case OUTLINE:
            graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
            //System.out.println("OUTLINE");
            break;
        case FILLED_IN:
            graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
            //System.out.println("FILLED IN");
            break;
        case OUTLINE_AND_FILLED_IN:
            graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
            graphics2d.setColor(secondary_color);
            graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
            //System.out.println("OUTLINE AND FILLED IN");
            break;
        }
    }
}
