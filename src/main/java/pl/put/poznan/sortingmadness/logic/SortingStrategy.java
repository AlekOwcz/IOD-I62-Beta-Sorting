package pl.put.poznan.sortingmadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

public interface SortingStrategy<T extends Comparable<T>>{
    ArrayList<T> sort(ArrayList<T> data);
}
