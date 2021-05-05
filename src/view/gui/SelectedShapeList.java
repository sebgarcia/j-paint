package view.gui;

import view.gui.Shape;

import java.util.Stack;

public class SelectedShapeList {
    private static final Stack<Shape> selectedShapeList = new Stack<Shape>();

    public static void add(Shape shape) {
        selectedShapeList.push(shape);
    }


    public static void clear(){
        selectedShapeList.clear();
    }

    public static Stack<Shape> getSelectedShapeList(){
        return selectedShapeList;
    }

}