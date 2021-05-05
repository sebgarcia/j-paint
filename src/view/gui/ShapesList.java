package view.gui;


import java.util.Stack;

import model.interfaces.IShape;
import view.gui.Shape;

public class ShapesList {
    private static final Stack<Shape> shapesList = new Stack<Shape>();

    public static void add(Shape shape) {
        shapesList.push(shape);
    }

    public  static void remove(){
        shapesList.pop();
    }

    public static Stack<Shape> getShapesList(){
        return shapesList;
    }

}
