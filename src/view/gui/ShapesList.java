package view.gui;


import java.util.ArrayList;
import java.util.List;

import model.interfaces.IShape;

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
