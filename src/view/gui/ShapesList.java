package view.gui;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.interfaces.IShape;
import view.gui.Shape;

public class ShapesList {
    private static final List<IShape> shapesList = new ArrayList<>();

    public static void add(IShape shape) {
        shapesList.add(shape);
    }

    public  static void remove(IShape s){
        shapesList.remove(s);
    }

    public static List<IShape> getShapesList(){
        return shapesList;
    }

}
