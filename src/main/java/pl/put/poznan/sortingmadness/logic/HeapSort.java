package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    private Logger logger;
    public HeapSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(InsertionSort.class);
    }

    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        return heap(data);
    }


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

    void heapify(ArrayList<JSONObject> data, int n, int i, String attribute)
    {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if (l < n && comparator.compare((T) data.get(l).get(attribute) , (T) data.get(largest)) > 0)
            largest = l;

        if (r < n && comparator.compare((T) data.get(r).get(attribute) ,(T) data.get(largest).get(attribute)) > 0)
            largest = r;
        if (largest != i)
        {
            JSONObject swap = data.get(i);
            data.set(i, data.get(largest));
            data.set(largest, swap);
            heapify(data, n, largest, attribute);
        }
    }
}