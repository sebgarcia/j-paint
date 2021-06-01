package view.gui;


import model.interfaces.IShape;

import java.util.ArrayList;
import java.util.List;

public class Clipboard {
    private static final List<IShape> clipboard = new ArrayList<>();

    public static void add(IShape shape) {
        clipboard.add(shape);
    }

    public static void remove(IShape shape){clipboard.remove(shape); }

    public static void clear(){
        clipboard.clear();
    }

    public static List<IShape> getClipboard(){
        return clipboard;
    }

}