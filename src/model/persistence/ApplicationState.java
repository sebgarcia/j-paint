package model.persistence;

import model.interfaces.ICommand;
import view.gui.*;
import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import model.MouseMode;
import model.dialogs.DialogProvider;
import model.interfaces.IApplicationState;
import model.interfaces.IDialogProvider;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

import java.io.IOException;

public class ApplicationState implements IApplicationState {
    private final IUiModule uiModule;
    private final IDialogProvider dialogProvider;

    private ShapeType activeShapeType;
    private ShapeColor activePrimaryColor;
    private ShapeColor activeSecondaryColor;
    private ShapeShadingType activeShapeShadingType;
    private MouseMode activeMouseMode;
    private PaintCanvasBase paintCanvasBase;

    public ApplicationState(IUiModule uiModule) {
        this.uiModule = uiModule;
        this.dialogProvider = new DialogProvider(this);
        setDefaults();
    }

    @Override
    public void setActiveShape() {
        activeShapeType = uiModule.getDialogResponse(dialogProvider.getChooseShapeDialog());
    }

    @Override
    public void setActivePrimaryColor() {
        activePrimaryColor = uiModule.getDialogResponse(dialogProvider.getChoosePrimaryColorDialog());
    }

    @Override
    public void setActiveSecondaryColor() {
        activeSecondaryColor = uiModule.getDialogResponse(dialogProvider.getChooseSecondaryColorDialog());
    }

    @Override
    public void setActiveShadingType() {
        activeShapeShadingType = uiModule.getDialogResponse(dialogProvider.getChooseShadingTypeDialog());
    }

    @Override
    public void setActiveStartAndEndPointMode() {
        activeMouseMode = uiModule.getDialogResponse(dialogProvider.getChooseStartAndEndPointModeDialog());
    }

    @Override
    public void setUndo() {
        CommandHistory.undo();
    }

    @Override
    public void setRedo(){
        CommandHistory.redo();
    }

    public void setCopy() {
        try {
            ICommand c = new CopyCommand();
            c.run();
        } catch (IOException e){
            System.out.println("Copy failed");
        }
    }

    @Override
    public ShapeType getActiveShapeType() {
        return activeShapeType;
    }

    @Override
    public ShapeColor getActivePrimaryColor() {
        return activePrimaryColor;
    }

    @Override
    public ShapeColor getActiveSecondaryColor() {
        return activeSecondaryColor;
    }

    @Override
    public ShapeShadingType getActiveShapeShadingType() {
        return activeShapeShadingType;
    }

    @Override
    public MouseMode getActiveMouseMode() {
        return activeMouseMode;
    }

    public void setPaintCanvasBase(PaintCanvasBase paintCanvasBase) {
        this.paintCanvasBase = paintCanvasBase;
    }

    public PaintCanvasBase getPaintCanvasBase(){return paintCanvasBase;}

    public void setPaste(){
        try {
            ICommand c = new PasteCommand();
            c.run();
        } catch (IOException e){
            System.out.println("Copy failed");
        }
    }

    @Override
    public void setDelete() {
        try {
            ICommand c = new DeleteCommand(this.paintCanvasBase);
            c.run();
        } catch (IOException e){
            System.out.println("Delete command failed");
        }
    }

    public void setGroup(){
        try{
            ICommand c = new GroupCommand(paintCanvasBase);
            c.run();
        }catch(IOException e){
            System.out.println("GroupCommandFail");
        }
    }

    private void setDefaults() {
        activeShapeType = ShapeType.RECTANGLE;
        activePrimaryColor = ShapeColor.BLUE;
        activeSecondaryColor = ShapeColor.GREEN;
        activeShapeShadingType = ShapeShadingType.FILLED_IN;
        activeMouseMode = MouseMode.DRAW;
    }
}
