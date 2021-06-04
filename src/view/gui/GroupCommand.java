package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GroupCommand implements ICommand, IUndoable {

    public PaintCanvasBase pcb;
    public IShape group;
    List<IShape> nullShapes = new ArrayList<>();

    public GroupCommand(PaintCanvasBase pcb){
        this.pcb = pcb;
        nullShapes.add(new NullShape());
    }
    @Override
    public void run() throws IOException {
        this.group = new Group(pcb,nullShapes);
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
