package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IUndoable;
import model.persistence.ApplicationState;
import view.interfaces.PaintCanvasBase;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CopyCommand implements ICommand {

    public CopyCommand(){ }

    @Override
    public void run() throws IOException {
        Clipboard.clear();
        for (Shape s : SelectedShapeList.getSelectedShapeList()){
            Clipboard.add(s);
        }
        System.out.println(Clipboard.getClipboard());
    }
    
}
