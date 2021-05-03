package view.gui;

import model.ShapeColor;
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
    int[] xValues = new int[3];
    int[] yValues = new int[3];

    DrawTriangleStrategy(PaintCanvasBase paintCanvas, MyPoint startPoint, MyPoint endPoint, ApplicationState appState){
        this.paintCanvas = paintCanvas;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.appState = appState;
        this.xValues[0] = startPoint.getX();
        this.xValues[1] = endPoint.getX();
        this.xValues[2] = startPoint.getX();

        this.yValues[0] = startPoint.getY();
        this.yValues[1] = endPoint.getY();
        this.yValues[2] = endPoint.getY();

    }

    public void draw(){
        graphics2d = paintCanvas.getGraphics2D();
        ShapeColor current_color = appState.getActivePrimaryColor();
        //System.out.println(current_color);
        graphics2d.setColor(Color.BLUE);
        //graphics2d.fillRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        graphics2d.fillPolygon(xValues,yValues,3);
    }
}
