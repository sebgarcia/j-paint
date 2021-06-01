package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IShape;

import java.io.IOException;


public class CopyCommand implements ICommand {

    public CopyCommand(){ }

    @Override
    public void run() throws IOException {
        Clipboard.clear();
        for (IShape s : SelectedShapeList.getSelectedShapeList()){
            Clipboard.add(s);
        }
        System.out.println(Clipboard.getClipboard());
    }

}
