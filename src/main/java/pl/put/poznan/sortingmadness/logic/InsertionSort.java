package pl.put.poznan.sortingmadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    public InsertionSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        for(int i = 1; i < data.size(); i++) {
            int j = i - 1 ;
            T elem = data.get(i);
            while (j >= 0 && comparator.compare(elem, data.get(j)) < 0 ) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, elem);
        }
        return data;
    }
}
