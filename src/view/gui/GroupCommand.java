package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;

public class GroupCommand implements ICommand, IUndoable {

    public PaintCanvasBase pcb;
    IShape group;

    public GroupCommand(PaintCanvasBase pcb){
        this.pcb = pcb;
    }
    @Override
    public void run() throws IOException {
        this.group = new Group(pcb);
        group.run();
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        group.undo();
    }

    @Override
    public void redo() {
        group.redo();
    }
}
