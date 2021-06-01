package view.gui;


import model.interfaces.IShape;

import java.util.ArrayList;
import java.util.List;

public class SelectedShapeList {
    private static final List<IShape> selectedShapeList = new ArrayList<>();

    public static void add(IShape shape) {
        selectedShapeList.add(shape);
    }

    public static void remove(IShape shape){selectedShapeList.remove(shape); }

    public static void clear(){
        selectedShapeList.clear();
    }

    public static List<IShape> getSelectedShapeList(){
        return selectedShapeList;
    }

}