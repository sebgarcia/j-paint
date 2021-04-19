package view.gui;
import java.awt.*;
import view.interfaces.PaintCanvasBase;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import model.persistence.ApplicationState;

import view.gui.MyPoint;
import model.interfaces.ICommand;
import view.gui.CommandHistory;


public class MyMouseListener extends MouseAdapter {

    PaintCanvasBase paintCanvas;
    MyPoint startPoint;
    MyPoint endPoint;
    ICommand command;
    ApplicationState appState;


    public MyMouseListener(PaintCanvasBase paintCanvas, ApplicationState appState){

        this.paintCanvas = paintCanvas;
        this.appState = appState;
    }

    public void mouseReleased(MouseEvent e){
        System.out.print("Mouse Released");
        endPoint = new MyPoint(e.getX(),e.getY());
        command = new DrawCommand(paintCanvas, startPoint,endPoint);

        try {
            command.run();
        } catch(IOException ex){
            System.out.print("Something went wrong");
        }





        //Graphics2D graphics2d = paintCanvas.getGraphics2D();
        //graphics2d.setColor(Color.BLUE);
        //graphics2d.drawRect(startPoint.x, startPoint.y, (endPoint.x-startPoint.x), (endPoint.y- startPoint.y));
        //graphics2d.setColor(Color.BLUE);
        //graphics2d.fillRect(12, 13, 200, 400);
    }

    public void mousePressed(MouseEvent e){
        //System.out.print("Mouse Pressed");
        startPoint = new MyPoint(e.getX(),e.getY());
    }

}