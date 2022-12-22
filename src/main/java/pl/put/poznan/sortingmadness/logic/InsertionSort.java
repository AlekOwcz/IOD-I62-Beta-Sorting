package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;

public class InsertionSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    Comparator<Double> numbericObjectComparator;

    Comparator<String> stringObjectComparator;
    private Logger logger;
    public InsertionSort(Comparator<T> comparator) {
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(InsertionSort.class);
    }
    public InsertionSort(Comparator<Double> comparator, int i) {
        this.numbericObjectComparator = comparator;
        logger = LoggerFactory.getLogger(InsertionSort.class);
    }

    public InsertionSort(Comparator<String> comparator, char i) {
        this.stringObjectComparator = comparator;
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
