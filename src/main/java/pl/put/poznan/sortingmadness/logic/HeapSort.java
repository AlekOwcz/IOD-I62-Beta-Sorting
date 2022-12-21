package pl.put.poznan.sortingmadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

public class HeapSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;

    public HeapSort(Comparator<T> comparator){
        this.comparator = comparator;
    }

    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        return heap(data);
    }

    private ArrayList<T> heap(ArrayList<T> data)
    {
        int n = data.size();
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(data, n, i);

        for (int i=n-1; i>=0; i--)
        {
            T tmp = data.get(0);
            data.set(0, data.get(i));
            data.set(i, tmp);
            heapify(data, i, 0);
        }
        return data;
    }

    void heapify(ArrayList<T> data, int n, int i)
    {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if (l < n && comparator.compare(data.get(l) , data.get(largest)) > 0)
            largest = l;

        if (r < n && comparator.compare(data.get(r) , data.get(largest)) > 0)
            largest = r;
        if (largest != i)
        {
            T swap = data.get(i);
            data.set(i, data.get(largest));
            data.set(largest, swap);
            heapify(data, n, largest);
        }
    }
}