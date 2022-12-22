package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;

public class SelectionSort<T extends  Comparable<T>> implements SortingStrategy<T> {

    Comparator<T> comparator;
    Comparator<Double> numbericObjectComparator;

    Comparator<String> stringObjectComparator;
    private Logger logger;
    public SelectionSort(Comparator<T> comparator){
        this.comparator = comparator;
        logger = LoggerFactory.getLogger(SelectionSort.class);
    }
    public SelectionSort(Comparator<Double> comparator, int i) {
        this.numbericObjectComparator = comparator;
        logger = LoggerFactory.getLogger(SelectionSort.class);
    }

    public SelectionSort(Comparator<String> comparator, char i) {
        this.stringObjectComparator = comparator;
        logger = LoggerFactory.getLogger(SelectionSort.class);
    }
    @Override
    public ArrayList<T> sort(ArrayList<T> data) {
        logger.info("[INFO] Start of sorting");
        for(int i = 0; i < data.size(); i++) {
            T max = data.get(i);
            int index = i;
            for(int j = i; j < data.size(); j++) {
                if (comparator.compare(max , data.get(j)) > 0) {
                    max = data.get(j);
                    index = j;
                }
            }
            T tmp = data.get(i);
            data.set(i, data.get(index));
            data.set(index, tmp);
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
            for (int i = 0; i < data.size(); i++) {
                double max = Double.parseDouble(data.get(i).get(attribute).toString());
                int index = i;
                for (int j = i; j < data.size(); j++) {
                    if (numbericObjectComparator.compare(max, Double.parseDouble(data.get(j).get(attribute).toString())) > 0) {
                        max = Double.parseDouble( data.get(j).get(attribute).toString());
                        index = j;
                    }
                }
                JSONObject tmp = data.get(i);
                data.set(i, data.get(index));
                data.set(index, tmp);
            }
            logger.debug("[DEBUG] List state {}", data);
        }
        if(data.get(0).get(attribute) instanceof String) {
            for (int i = 0; i < data.size(); i++) {
                String max = data.get(i).get(attribute).toString();
                int index = i;
                for (int j = i; j < data.size(); j++) {
                    if (stringObjectComparator.compare(max,  data.get(j).get(attribute).toString()) > 0) {
                        max = data.get(j).get(attribute).toString();
                        index = j;
                    }
                }
                JSONObject tmp = data.get(i);
                data.set(i, data.get(index));
                data.set(index, tmp);
            }
            logger.debug("[DEBUG] List state {}", data);
        }
        logger.info("[INFO] End of sorting");
        return data;
    }
}
