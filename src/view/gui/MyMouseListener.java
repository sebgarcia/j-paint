package view.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener extends MouseAdapter {

    public void mouseClicked (MouseEvent e){
        System.out.print("Mouse Clicked");
    }

    public void mouseReleased(MouseEvent e){
        System.out.print("Mouse Released");
    }

    public void mousePressed(MouseEvent e){
        System.out.print("Mouse Pressed");
    }



}