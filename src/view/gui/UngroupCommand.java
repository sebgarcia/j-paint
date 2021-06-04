package view.gui;

import model.interfaces.ICommand;
import model.interfaces.IShape;
import model.interfaces.IUndoable;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UngroupCommand implements ICommand, IUndoable {
    public PaintCanvasBase paintCanvasBase;
    List<IShape> tempSelectedShapesList = new ArrayList<>();
    List<IShape> ungroupedGroups = new ArrayList<>();
    List<IShape> groupComponentShapes = new ArrayList<>();
    public Graphics2D graphics2D;

    public UngroupCommand(PaintCanvasBase pcb){
        this.paintCanvasBase = pcb;
        this.graphics2D=paintCanvasBase.getGraphics2D();
    }

    @Override
    public void run() throws IOException {
        ungroupShapes();
        CommandHistory.add(this);
    }

    public void ungroupShapes(){
        for(IShape ishape: SelectedShapeList.getSelectedShapeList()){
            tempSelectedShapesList.add(ishape);
        }
        for (IShape s: tempSelectedShapesList){
            if (s instanceof Group){
                Group group_to_ungroup = (Group) s;
                ungroupedGroups.add(s);
                for(IShape j: group_to_ungroup.ShapeGroup){
                    groupComponentShapes.add(j);
                    SelectedShapeList.add(j);
                    ShapesList.add(j);
                }
                SelectedShapeList.remove(s);
                ShapesList.remove(s);
            }

        }

        clearCanvas();
        redrawSelectedShapes();



    }
    @Override
    public void undo() {
        for(IShape s: groupComponentShapes){
            ShapesList.remove(s);
            SelectedShapeList.remove(s);
        }
        for (IShape s: ungroupedGroups){
            SelectedShapeList.add(s);
            ShapesList.add(s);
        }
        clearCanvas();
        redrawSelectedShapes();
    }

    @Override
    public void redo() {
        ungroupShapes();
    }

    public void redrawSelectedShapes(){
        ShapesList.getShapesList().forEach(IShape::draw);
        SelectedShapeList.getSelectedShapeList().forEach(s-> {
            new SelectedShapeOutline(s).run();
        });
    }


    public void clearCanvas(){
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0, paintCanvasBase.getWidth(), paintCanvasBase.getHeight());
    }
}
