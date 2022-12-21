package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;

public class QuickSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    private Logger logger;
    public QuickSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(QuickSort.class);
    }
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        logger.info("[INFO] Start of sorting");
        quick(data, 0, data.size());
        logger.info("[INFO] End of sorting");
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
            while(position < r - 1 && position != pivot && comparator.compare(data.get(position), data.get(pivot)) == 0) {
                position++;
            }
            if(comparator.compare(data.get(pivot), data.get(position)) < 0) {
                T tmp = data.get(pivot);
                data.set(pivot, data.get(position));
                data.set(position, tmp);
                pivot = position;
            }
            logger.debug("[DEBUG] List state {}", data);
            quick(data, l, pivot);
            quick(data, pivot, r);
        }
    }

    @Override
    public ArrayList<JSONObject> sort(ArrayList<JSONObject> data, String attribute) {
        logger.info("[INFO] Start of sorting");
        quick(data, 0, data.size(), attribute);
        logger.info("[INFO] End of sorting");
        return  data;
    }

    private void quick(ArrayList<JSONObject> data, int l, int r, String attribute) {
        if (r - l > 1) {
            int pivot = l + (r - l) / 2;
            int position = l;
            for (int i = l; i < r; i++) {
                if (comparator.compare((T) data.get(i).get(attribute), (T) data.get(pivot).get(attribute)) < 0) {
                    JSONObject tmp = data.get(i);
                    data.set(i, data.get(position));
                    data.set(position,  tmp);
                    if (position == pivot) {
                        pivot = i;
                    }
                    position++;
                }
            }
            while(position < r - 1 && position != pivot && comparator.compare((T) data.get(position).get(attribute), (T) data.get(pivot).get(attribute)) == 0) {
                position++;
            }
            if(comparator.compare((T) data.get(pivot).get(attribute), (T) data.get(position).get(attribute)) < 0) {
                JSONObject tmp = data.get(pivot);
                data.set(pivot, data.get(position));
                data.set(position, tmp);
                pivot = position;
            }
            logger.debug("[DEBUG] List state {}", data);
            quick(data, l, pivot, attribute);
            quick(data, pivot, r, attribute);
        }
    }
}
