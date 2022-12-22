package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Comparator;

public class BubbleSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;

    Comparator<Double> numbericObjectComparator;

    Comparator<String> stringObjectComparator;
    private Logger logger;
    public BubbleSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(BubbleSort.class);
    }

    public BubbleSort(Comparator<Double> comparator, int i) {
        this.numbericObjectComparator = comparator;
        logger = LoggerFactory.getLogger(BubbleSort.class);
    }

    public BubbleSort(Comparator<String> comparator, char i) {
        this.stringObjectComparator = comparator;
        logger = LoggerFactory.getLogger(BubbleSort.class);
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