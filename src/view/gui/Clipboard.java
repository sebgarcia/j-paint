package view.gui;


import java.util.ArrayList;
import java.util.List;

public class Clipboard {
    private static final List<Shape> clipboard = new ArrayList<>();

    public static void add(Shape shape) {
        clipboard.add(shape);
    }

    public static void remove(Shape shape){clipboard.remove(shape); }

    public static void clear(){
        clipboard.clear();
    }

    public static List<Shape> getClipboard(){
        return clipboard;
    }

}