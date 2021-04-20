package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import view.gui.CommandHistory;

public class UndoCommand implements ICommand, IUndoable {
    PaintCanvasBase paintCanvas;
    Graphics2D graphics2d = paintCanvas.getGraphics2D();

    public void run(){

    }

    public void undo(){

    }

    public void redo(){

    }

    public void rerun(){

    }

}
