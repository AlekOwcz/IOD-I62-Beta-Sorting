package pl.put.poznan.sortingmadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

public class SelectionSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;

    public SelectionSort(Comparator<T> comparator){
        this.comparator = comparator;
    }
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        for(int i = 0; i < data.size(); i++) {
            T max = data.get(i);
            int index = i;
            for(int j = i; j < data.size(); j++) {
                if (comparator.compare(max , data.get(j)) > 0) {
                    max = data.get(j);
                    index = j;
                }
            }
            T tmp = data.get(i);
            data.set(i, data.get(index));
            data.set(index, tmp);
        }
        return data;
    }
}
