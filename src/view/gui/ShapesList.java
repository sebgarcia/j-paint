package view.gui;


import java.util.Stack;

import model.interfaces.IShape;

public class ShapesList {
    private static final Stack<IShape> shapesList = new Stack<IShape>();

    public static void add(IShape shape) {
        shapesList.push(shape);
    }

    public  static void remove(){
        shapesList.pop();
    }

    public static Stack<IShape> getShapesList(){
        return shapesList;
    }

}
