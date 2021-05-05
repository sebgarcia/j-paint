package view.gui;
import model.MouseMode;
import view.interfaces.PaintCanvasBase;
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


    public MyMouseListener(PaintCanvasBase paintCanvas, ApplicationState appState){

        this.paintCanvas = paintCanvas;
        this.appState = appState;
    }

    public void mouseReleased(MouseEvent e){
        endPoint = new MyPoint(e.getX(),e.getY());
        mouseMode = appState.getActiveMouseMode();
        switch(mouseMode){
            case DRAW:
                command = new Shape(paintCanvas, startPoint,endPoint, appState);
                try {
                    command.run();
                } catch(IOException ex){
                    System.out.print("Something went wrong with your Draw mode");
                }
                break;
            case MOVE:
                break;
            case SELECT:
                command = new SelectCommand(paintCanvas, startPoint, endPoint, appState);
                try{
                    command.run();
                }catch(IOException ex){
                    System.out.println("Something went wrong with your Select command");
                }
                break;
        }


        //Graphics2D graphics2d = paintCanvas.getGraphics2D();
        //graphics2d.setColor(Color.BLUE);
        //graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        //graphics2d.setColor(Color.BLUE);
        //graphics2d.fillRect(12, 13, 200, 400);
    }

    public void mousePressed(MouseEvent e){
        startPoint = new MyPoint(e.getX(),e.getY());
    }

}