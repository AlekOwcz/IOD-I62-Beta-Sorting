package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
/**
 * Class implementing the InsertionSort algorithm.
 * @param <T> - data type to sort.
 */
public class InsertionSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    Comparator<Double> numbericObjectComparator;

    Comparator<String> stringObjectComparator;
    private final Logger logger;
    /**
     * Constructor for one dimensional arrays.
     * @param comparator - comparator object of generic type.
     */
    public InsertionSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(InsertionSort.class);
    }
    /**
     * Constructor for JSONObject arrays sorted by numeric attribute.
     * @param comparator - comparator object of Double type.
     * @param i - redundant argument used temporarily to distinguish constructors. To be changed in future releases.
     */
    public InsertionSort(Comparator<Double> comparator, int i) {
        this.numbericObjectComparator = comparator;
        logger = LoggerFactory.getLogger(InsertionSort.class);
    }
    /**
     * Constructor for JSONObject arrays sorted by string attribute.
     * @param comparator - comparator object of String type.
     * @param i - redundant argument used temporarily to distinguish constructors. To be changed in future releases.
     */
    public InsertionSort(Comparator<String> comparator, char i) {
        this.stringObjectComparator = comparator;
        logger = LoggerFactory.getLogger(InsertionSort.class);
    }
    /**
     * Main sorting method of the class for one dimensional arrays.
     * @param data - input data in the form of ArrayList of type either Double or String.
     * @return Sorted array.
     */
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
    /**
     * Main sorting method of the class for JSONObject arrays, implements algorithm directly.
     * @param data - an ArrayList of JSONObject
     * @param attribute - attribute of the JSON object by which the data should be sorted
     * @return sorted array
     */
    @Override
    public ArrayList<JSONObject> sort(ArrayList<JSONObject> data, String attribute) {
        logger.info("[INFO] Start of sorting");
        if(data.get(0).get(attribute) instanceof Double ||
                data.get(0).get(attribute) instanceof Float ||
                data.get(0).get(attribute) instanceof Integer) {
            for (int i = 1; i < data.size(); i++) {
                int j = i - 1;
                JSONObject elem = data.get(i);
                while (j >= 0 && numbericObjectComparator.compare(Double.parseDouble(elem.get(attribute).toString()),Double.parseDouble(data.get(j).get(attribute).toString())) < 0) {
                    data.set(j + 1, data.get(j));
                    j--;
                }
                data.set(j + 1, elem);
                logger.debug("[DEBUG] List state {}", data);
            }
        }
        if(data.get(0).get(attribute) instanceof String) {
            for (int i = 1; i < data.size(); i++) {
                int j = i - 1;
                JSONObject elem = data.get(i);
                while (j >= 0 && stringObjectComparator.compare( elem.get(attribute).toString(),  data.get(j).get(attribute).toString()) < 0) {
                    data.set(j + 1, data.get(j));
                    j--;
                }
                data.set(j + 1, elem);
                logger.debug("[DEBUG] List state {}", data);
            }
        }
        logger.info("[INFO] End of sorting");
        return data;
    }
}
