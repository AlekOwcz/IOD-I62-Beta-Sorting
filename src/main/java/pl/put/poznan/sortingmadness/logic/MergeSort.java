package pl.put.poznan.sortingmadness.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;

public class MergeSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    private Logger logger;
    public MergeSort(Comparator<T> comparator){
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(MergeSort.class);
    }

    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        logger.info("[INFO] Start of sorting");
        return mergeSort(data);
    }

    private ArrayList<T> mergeSort(ArrayList<T> data){
        if (data.size() > 1) {
            ArrayList<T> left = mergeSort(new ArrayList<>(data.subList(0, data.size() / 2)));
            ArrayList<T> right = mergeSort(new ArrayList<>(data.subList(data.size() / 2, data.size())));
            ArrayList<T> result = new ArrayList<>(data.size());
            int i = 0;
            int j = 0;
            while (i < left.size() || j < right.size()) {
                if ( i >= left.size()) {
                    result.add(right.get(j));
                    j++;
                } else if (j >= right.size()) {
                    result.add(left.get(i));
                    i++;
                } else if (comparator.compare(left.get(i), right.get(j)) < 0) {
                    result.add(left.get(i));
                    i++;
                } else {
                    result.add(right.get(j));
                    j++;
                }
            }
            logger.debug("[DEBUG] List state {}", data);
            return result;
        } else {
            logger.debug("[DEBUG] List state {}", data);
            return  data;
        }
    }
}
