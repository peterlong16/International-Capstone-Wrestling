package Utilities;

import game.Tile;

import java.util.ArrayList;
import java.util.Collections;

public class SortedList {
    private ArrayList list = new ArrayList();

    public Tile first() {
        return (Tile) list.get(0);
    }

    public void clear() {
        list.clear();
    }

    public void add(Tile o) {
        list.add(o);
        Collections.sort(list);
    }

    public void remove(Tile o) {
        list.remove(o);
    }

    public int size() {
        return list.size();
    }

    public boolean contains(Tile o) {
        return list.contains(o);
    }
}
