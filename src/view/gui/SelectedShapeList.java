package view.gui;


import java.util.ArrayList;
import java.util.List;

public class SelectedShapeList {
    private static final List<Shape> selectedShapeList = new ArrayList<>();

    public static void add(Shape shape) {
        selectedShapeList.add(shape);
    }

    public static void remove(Shape shape){selectedShapeList.remove(shape); }

    public static void clear(){
        selectedShapeList.clear();
    }

    public static List<Shape> getSelectedShapeList(){
        return selectedShapeList;
    }

}