package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class implementing the BubbleSort algorithm.
 * @param - data type to sort.
 */
public class BubbleSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;

    Comparator<Double> numbericObjectComparator;

    Comparator<String> stringObjectComparator;
    private Logger logger;

    /**
     * Constructor for one dimensional arrays.
     * @param comparator - comparator object of generic type.
     */
    public BubbleSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(BubbleSort.class);
    }

    /**
     * Constructor for JSONObject arrays sorted by numeric attribute.
     * @param comparator - comparator object of Double type.
     * @param i - redundant argument used temporarily to distinguish constructors. To be changed in future releases.
     */
    public BubbleSort(Comparator<Double> comparator, int i) {
        this.numbericObjectComparator = comparator;
        logger = LoggerFactory.getLogger(BubbleSort.class);
    }
    /**
     * Constructor for JSONObject arrays sorted by string attribute.
     * @param comparator - comparator object of String type.
     * @param i - redundant argument used temporarily to distinguish constructors. To be changed in future releases.
     */
    public BubbleSort(Comparator<String> comparator, char i) {
        this.stringObjectComparator = comparator;
        logger = LoggerFactory.getLogger(BubbleSort.class);
    }

    /**
     * Main sorting method of the class for one dimensional arrays, calls the bubble() method.
     * @param data - input data in the form of ArrayList where T should either be Double or String.
     * @return Sorted array.
     */
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        return bubble(data);
    }

    /**
     * Method implementing BubbleSort algorithm, should only be called by sort() method.
     * @param data - input data in the form of ArrayList<T> where T should either be Double or String.
     * @return Sorted array
     */
    private ArrayList<T> bubble(ArrayList<T> data){
        logger.info("[INFO] Start of sorting");
        int n = data.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (comparator.compare(data.get(j) , data.get(j+1)) > 0) {
                    T tmp = data.get(j);
                    data.set(j, data.get(j+1));
                    data.set(j+1, tmp);
                    logger.debug("[DEBUG] List state {}", data);
                }
        logger.info("[INFO] End of sorting");
        return data;
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
        int n = data.size();
        if(data.get(0).get(attribute) instanceof Double ||
                data.get(0).get(attribute) instanceof Float ||
                data.get(0).get(attribute) instanceof Integer) {
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (numbericObjectComparator.compare( Double.parseDouble(data.get(j).get(attribute).toString()), Double.parseDouble(data.get(j + 1).get(attribute).toString())) > 0) {
                        JSONObject tmp = data.get(j);
                        data.set(j, data.get(j + 1));
                        data.set(j + 1, tmp);
                        logger.debug("[DEBUG] List state {}", data);
                    }
        }
        if(data.get(0).get(attribute) instanceof String) {
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (stringObjectComparator.compare(data.get(j).get(attribute).toString(), data.get(j + 1).get(attribute).toString()) > 0) {
                        JSONObject tmp = data.get(j);
                        data.set(j, data.get(j + 1));
                        data.set(j + 1, tmp);
                        logger.debug("[DEBUG] List state {}", data);
                    }
        }
        logger.info("[INFO] End of sorting");
        return data;
    }
}
