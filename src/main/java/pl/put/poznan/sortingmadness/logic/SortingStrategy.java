package pl.put.poznan.sortingmadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

public interface SortingStrategy<T extends Comparable>{
    //TODO DECIDE ON RETURN TYPE
    //long time = -1;
    //TODO MEMBER LIST FOR KEEPING THE RESULT
    //public String sort();
    public  ArrayList<T>  sort(ArrayList<T> data);
}
