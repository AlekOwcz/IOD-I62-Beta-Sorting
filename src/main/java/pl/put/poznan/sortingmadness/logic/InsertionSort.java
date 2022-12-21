package pl.put.poznan.sortingmadness.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    private Logger logger;
    public InsertionSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(InsertionSort.class);
    }
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        logger.info("[INFO] Start of sorting");
        for(int i = 1; i < data.size(); i++) {
            int j = i - 1 ;
            T elem = data.get(i);
            while (j >= 0 && comparator.compare(elem, data.get(j)) < 0 ) {
                data.set(j + 1, data.get(j));
                j--;
            }
            data.set(j + 1, elem);
            logger.debug("[DEBUG] List state {}", data);
        }
        logger.info("[INFO] End of sorting");
        return data;
    }
}
