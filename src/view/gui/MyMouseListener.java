package view.gui;
import model.MouseMode;
import model.ShapeShadingType;
import model.ShapeType;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import model.persistence.ApplicationState;

import model.interfaces.ICommand;

public class MyMouseListener extends MouseAdapter {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    ICommand command;
    ApplicationState appState;
    MouseMode mouseMode;
    Color primary_color;
    Color secondary_color;
    ShapeShadingType current_shading_type;
    ShapeType shapeType;


    public MyMouseListener(PaintCanvasBase paintCanvas, ApplicationState appState){
        this.paintCanvas = paintCanvas;
        this.appState = appState;
    }

    public void mouseReleased(MouseEvent e){
        endPoint = new MyPoint(e.getX(),e.getY());
        mouseMode = appState.getActiveMouseMode();
        current_shading_type = appState.getActiveShapeShadingType();
        primary_color = appState.getActivePrimaryColor().getColor();
        secondary_color = appState.getActiveSecondaryColor().getColor();
        shapeType = appState.getActiveShapeType();
        switch(mouseMode){
            case DRAW:
                command = new ShapeBuilder()
                        .setPaintCanvas(paintCanvas)
                        .setStartPoint(startPoint)
                        .setEndPoint(endPoint)
                        .setPrimaryColor(primary_color)
                        .setSecondaryColor(secondary_color)
                        .setApplicationState(appState)
                        .setShapeType(shapeType)
                        .setShapeShadingType(current_shading_type)
                        .toShape();
                //command = new Shape(paintCanvas, startPoint, endPoint, appState, shapeType, current_shading_type, primary_color, secondary_color);
                break;
            case MOVE:
                command = new MoveCommand(paintCanvas, startPoint, endPoint, appState);
                break;
            case SELECT:
                command = new SelectCommand(paintCanvas, startPoint, endPoint, appState);
                break;
        }
        try{
            command.run();
        }catch(IOException ex){
            System.out.println("something went wrong");
        }

    }

    public void mousePressed(MouseEvent e){
        startPoint = new MyPoint(e.getX(),e.getY());
    }

}