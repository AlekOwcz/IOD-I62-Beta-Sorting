package pl.put.poznan.sortingmadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

public class HeapSort<T extends  Comparable<T>> implements SortingStrategy<T>  {

    Comparator<T> comparator;
    public HeapSort(Comparator<T> comparator){
        this.comparator = comparator;
    }
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        return null;
    }
}
