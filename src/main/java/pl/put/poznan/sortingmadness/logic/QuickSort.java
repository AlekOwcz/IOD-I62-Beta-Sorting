package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * Class implementing the QuickSort algorithm.
 * @param <T> - data type to sort.
 */
public class QuickSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;

    Comparator<Double> numbericObjectComparator;

    Comparator<String> stringObjectComparator;
    private Logger logger;
    /**
     * Constructor for one dimensional arrays.
     * @param comparator - comparator object of generic type.
     */
    public QuickSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(QuickSort.class);
    }
    /**
     * Constructor for JSONObject arrays sorted by numeric attribute.
     * @param comparator - comparator object of Double type.
     * @param i - redundant argument used temporarily to distinguish constructors. To be changed in future releases.
     */
    public QuickSort(Comparator<Double> comparator, int i) {
        this.numbericObjectComparator = comparator;
        logger = LoggerFactory.getLogger(QuickSort.class);
    }
    /**
     * Constructor for JSONObject arrays sorted by string attribute.
     * @param comparator - comparator object of String type.
     * @param i - redundant argument used temporarily to distinguish constructors. To be changed in future releases.
     */
    public QuickSort(Comparator<String> comparator, char i) {
        this.stringObjectComparator = comparator;
        logger = LoggerFactory.getLogger(QuickSort.class);
    }
    /**
     * Main sorting method of the class for one dimensional arrays, calls the quick() method.
     * @param data - input data in the form of ArrayList<T> where T should either be Double or String.
     * @return Sorted array.
     */
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        logger.info("[INFO] Start of sorting");
        quick(data, 0, data.size());
        logger.info("[INFO] End of sorting");
        return  data;
    }
    /**
     * Method implementing QuickSort algorithm, should only be called by sort() method.
     * @param data - input data in the form of ArrayList<T> where T should either be Double or String.
     * @return Sorted array
     */
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
    /**
     * Main sorting method of the class for JSONObject arrays, implements algorithm directly.
     * @param data - an ArrayList of JSONObject
     * @param attribute - attribute of the JSON object by which the data should be sorted
     * @return Sorted array
     */
    @Override
    public ArrayList<JSONObject> sort(ArrayList<JSONObject> data, String attribute) {
        logger.info("[INFO] Start of sorting");
        quick(data, 0, data.size(), attribute);
        logger.info("[INFO] End of sorting");
        return  data;
    }
    /**
     * Method implementing QuickSort algorithm, used by JSONObject sorting, should only be called by sort() method.
     * @param data - input data in the form of ArrayList<T> where T should either be Double or String.
     * @return sorted array
     */
    private void quick(ArrayList<JSONObject> data, int l, int r, String attribute) {
        if (r - l > 1) {
            int pivot = l + (r - l) / 2;
            int position = l;
            if(data.get(0).get(attribute) instanceof Double ||
                    data.get(0).get(attribute) instanceof Float ||
                    data.get(0).get(attribute) instanceof Integer) {
                for (int i = l; i < r; i++) {
                    if (numbericObjectComparator.compare(Double.parseDouble(data.get(i).get(attribute).toString()), Double.parseDouble(data.get(pivot).get(attribute).toString())) < 0) {
                        JSONObject tmp = data.get(i);
                        data.set(i, data.get(position));
                        data.set(position,  tmp);
                        if (position == pivot) {
                            pivot = i;
                        }
                        position++;
                    }
                }
                while(position < r - 1 && position != pivot && numbericObjectComparator.compare(Double.parseDouble(data.get(position).get(attribute).toString()) , Double.parseDouble(data.get(pivot).get(attribute).toString())) == 0) {
                    position++;
                }
                if(numbericObjectComparator.compare(Double.parseDouble(data.get(pivot).get(attribute).toString()), Double.parseDouble(data.get(position).get(attribute).toString())) < 0) {
                    JSONObject tmp = data.get(pivot);
                    data.set(pivot, data.get(position));
                    data.set(position, tmp);
                    pivot = position;
                }
            }
            if(data.get(0).get(attribute) instanceof String) {
                for (int i = l; i < r; i++) {
                    if (stringObjectComparator.compare(data.get(i).get(attribute).toString(), data.get(pivot).get(attribute).toString()) < 0) {
                        JSONObject tmp = data.get(i);
                        data.set(i, data.get(position));
                        data.set(position,  tmp);
                        if (position == pivot) {
                            pivot = i;
                        }
                        position++;
                    }
                }
                while(position < r - 1 && position != pivot && stringObjectComparator.compare(data.get(position).get(attribute).toString(), data.get(pivot).get(attribute).toString()) == 0) {
                    position++;
                }
                if(stringObjectComparator.compare(data.get(pivot).get(attribute).toString(), data.get(position).get(attribute).toString()) < 0) {
                    JSONObject tmp = data.get(pivot);
                    data.set(pivot, data.get(position));
                    data.set(position, tmp);
                    pivot = position;
                }
            }

            logger.debug("[DEBUG] List state {}", data);
            quick(data, l, pivot, attribute);
            quick(data, pivot, r, attribute);
        }
    }
}
