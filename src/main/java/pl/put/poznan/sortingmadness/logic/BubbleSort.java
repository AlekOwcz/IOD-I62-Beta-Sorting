package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Comparator;

public class BubbleSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    private Logger logger;
    public BubbleSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(InsertionSort.class);
    }

    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        return bubble(data);
    }


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

    @Override
    public ArrayList<JSONObject> sort(ArrayList<JSONObject> data, String attribute) {
        logger.info("[INFO] Start of sorting");
        int n = data.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (comparator.compare((T) data.get(j).get(attribute) , (T) data.get(j+1).get(attribute)) > 0) {
                    JSONObject tmp = data.get(j);
                    data.set(j, data.get(j+1));
                    data.set(j+1, tmp);
                    logger.debug("[DEBUG] List state {}", data);
                }
        logger.info("[INFO] End of sorting");
        return data;
    }
}