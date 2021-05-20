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
    Boolean isOutline;



    DrawRectangleStrategy(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState, ShapeShadingType current_shading_type, Color primary_color, Color secondary_color, Boolean isOutline){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.appState = appState;
        this.current_shading_type = current_shading_type;
        this.primary_color = primary_color;
        this.secondary_color = secondary_color;
        this.isOutline = isOutline;
    }

    public void draw(){
        graphics2d = paintCanvas.getGraphics2D();
        graphics2d.setColor(primary_color);
        if (isOutline){
            //if isOutline flag is selected, then create the outline stroke.
            // Dotted line formatting found in this stack overflow post:
            // https://stackoverflow.com/questions/21989082/drawing-dashed-line-in-java/21989406
            graphics2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        }
        switch(current_shading_type){
            case OUTLINE:
                graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
            break;
            case FILLED_IN:
                graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
                break;
            case OUTLINE_AND_FILLED_IN:
                graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
                graphics2d.setColor(secondary_color);
                graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
                break;
            }
    }


}
