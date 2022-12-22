package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Comparator;
/**
 * Class implementing the HeapSort algorithm.
 * @param <T> - data type to sort.
 */
public class HeapSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    Comparator<Double> numbericObjectComparator;
    Comparator<String> stringObjectComparator;
    private Logger logger;
    /**
     * Constructor for one dimensional arrays.
     * @param comparator - comparator object of generic type.
     */
    public HeapSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(HeapSort.class);
    }
    /**
     * Constructor for JSONObject arrays sorted by numeric attribute.
     * @param comparator - comparator object of Double type.
     * @param i - redundant argument used temporarily to distinguish constructors. To be changed in future releases.
     */
    public HeapSort(Comparator<Double> comparator, int i) {
        this.numbericObjectComparator = comparator;
        logger = LoggerFactory.getLogger(HeapSort.class);
    }
    /**
     * Constructor for JSONObject arrays sorted by string attribute.
     * @param comparator - comparator object of String type.
     * @param i - redundant argument used temporarily to distinguish constructors. To be changed in future releases.
     */
    public HeapSort(Comparator<String> comparator, char i) {
        this.stringObjectComparator = comparator;
        logger = LoggerFactory.getLogger(HeapSort.class);
    }

    /**
     * Main sorting method of the class for one dimensional arrays, calls the heap() method.
     * @param data - input data in the form of ArrayList of type either Double or String.
     * @return Sorted array.
     */
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        return heap(data);
    }

    /**
     * Method implementing HeapSort algorithm, should only be called by sort() method.
     * @param data - input data in the form of ArrayList of type either Double or String.
     * @return sorted array
     */
    private ArrayList<T> heap(ArrayList<T> data)
    {
        logger.info("[INFO] Start of sorting");
        int n = data.size();
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(data, n, i);

        for (int i=n-1; i>=0; i--)
        {
            T tmp = data.get(0);
            data.set(0, data.get(i));
            data.set(i, tmp);
            heapify(data, i, 0);
            logger.debug("[DEBUG] List state {}", data);
        }
        logger.info("[INFO] End of sorting");
        return data;
    }

    /**
     * Creates the heap, only called through heap() method.
     * @param data - data to be sorted.
     * @param n - size of heap.
     * @param i - index of node.
     */
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
    /**
     * Main sorting method of the class for JSONObject arrays, implements algorithm directly.
     * @param data - an ArrayList of JSONObject
     * @param attribute - attribute of the JSON object by which the data should be sorted
     * @return sorted array
     */
    @Override
    public ArrayList<JSONObject> sort(ArrayList<JSONObject> data, String attribute) {
        logger.info("[INFO] Start of sorting");
        int n = data.size();
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(data, n, i, attribute);

        for (int i=n-1; i>=0; i--)
        {
            JSONObject tmp = data.get(0);
            data.set(0, data.get(i));
            data.set(i, tmp);
            heapify(data, i, 0, attribute);
            logger.debug("[DEBUG] List state {}", data);
        }
        logger.info("[INFO] End of sorting");
        return data;
    }
    /**
     * Creates the heap, used by the JSON object sorting method, only called through sort() method.
     * @param data - data to be sorted.
     * @param n - size of heap.
     * @param i - index of node.
     */
    void heapify(ArrayList<JSONObject> data, int n, int i, String attribute)
    {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if(data.get(0).get(attribute) instanceof Double ||
                data.get(0).get(attribute) instanceof Float ||
                data.get(0).get(attribute) instanceof Integer) {
            if (l < n && numbericObjectComparator.compare(Double.parseDouble(data.get(l).get(attribute).toString()), Double.parseDouble(data.get(largest).get(attribute).toString())) > 0)
                largest = l;

            if (r < n && numbericObjectComparator.compare(Double.parseDouble(data.get(r).get(attribute).toString()), Double.parseDouble(data.get(largest).get(attribute).toString())) > 0)
                largest = r;
            if (largest != i) {
                JSONObject swap = data.get(i);
                data.set(i, data.get(largest));
                data.set(largest, swap);
                heapify(data, n, largest, attribute);
            }
        }
        if(data.get(0).get(attribute) instanceof String){
            if (l < n && stringObjectComparator.compare(data.get(l).get(attribute).toString(), data.get(largest).get(attribute).toString()) > 0)
                largest = l;

            if (r < n && stringObjectComparator.compare(data.get(r).get(attribute).toString(), data.get(largest).get(attribute).toString()) > 0)
                largest = r;
            if (largest != i) {
                JSONObject swap = data.get(i);
                data.set(i, data.get(largest));
                data.set(largest, swap);
                heapify(data, n, largest, attribute);
            }
        }
    }
}
