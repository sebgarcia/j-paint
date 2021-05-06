package view.gui;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.interfaces.IShape;
import view.gui.Shape;

public class ShapesList {
    private static final List<Shape> shapesList = new ArrayList<>();

    public static void add(Shape shape) {
        shapesList.add(shape);
    }

    public  static void remove(Shape s){
        shapesList.remove(s);
    }

    public static List<Shape> getShapesList(){
        return shapesList;
    }

}
