package pl.put.poznan.sortingmadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

public class QuickSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;

    public QuickSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        quick(data, 0, data.size());
        return  data;
    }

    private void quick(ArrayList<T> data, int l, int r) {
        if (r - l > 1) {
            int pivot = l + (r - l) / 2;
            int position = l;
            for (int i = l; i < r; i++) {
                if (comparator.compare(data.get(i), data.get(pivot)) < 0) {
                    T tmp = data.get(i);
                    data.set(i, data.get(position));
                    data.set(position, tmp);
                    if (position == pivot) {
                        pivot = i;
                    }
                    position++;
                }
            }
            T tmp = data.get(pivot);
            data.set(pivot, data.get(position));
            data.set(position, tmp);
            pivot = position;
            quick(data, l, pivot);
            quick(data, pivot, r);
        }
    }
}
