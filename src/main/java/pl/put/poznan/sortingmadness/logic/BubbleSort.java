package pl.put.poznan.sortingmadness.logic;

import java.util.ArrayList;
import java.util.Comparator;

public class BubbleSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;

    public BubbleSort(Comparator<T> comparator){
        this.comparator = comparator;
    }

    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        return bubble(data);
    }

    private ArrayList<T> bubble(ArrayList<T> data){

        int n = data.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (comparator.compare(data.get(j) , data.get(j+1)) > 0) {
                    T tmp = data.get(j);
                    data.set(j, data.get(j+1));
                    data.set(j+1, tmp);
                }
        return data;
    }
}