package pl.put.poznan.sortingmadness.logic;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.sortingmadness.rest.SortingMadnessController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;

public class SortingContext<T extends  Comparable<T>> {

    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);
    private ArrayList<T> data;
    private ArrayList<JSONObject> jsonData;
    private final ArrayList<Long> times = new ArrayList<>();
    private JSONArray algorithms;
    private ArrayList<T> sortedData = null;
    private ArrayList<JSONObject> sortedObjectData = null;
    private String attribute = null;

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
    public void setJsonData(ArrayList<JSONObject> data) {
        this.jsonData = data;
    }
    public void setAlgorithms(JSONArray algorithms) {
        this.algorithms = algorithms;
    }
    public void setAttribute(String attribute){
        this.attribute = attribute;
    }
    public ArrayList<T> getSortedData() { return sortedData; }
    public ArrayList<JSONObject> getSortedJsonData() { return sortedObjectData; }
    public ArrayList<Long> getTimes() { return times; }

    public void sort() {
        boolean sorted = false;
        SortingStrategy<T> strat = null;
        for(Object alg : algorithms) {
            switch(alg.toString().toLowerCase()){
                case "bubble":
                    strat = new BubbleSort<T>(Comparator.naturalOrder());
                    break;
                case "insertion":
                    strat = new InsertionSort<T>(Comparator.naturalOrder());
                    break;
                case "selection":
                    strat = new SelectionSort<T>(Comparator.naturalOrder());
                    break;
                case "quick":
                    strat = new QuickSort<T>(Comparator.naturalOrder());
                    break;
                case "merge":
                    strat = new MergeSort<T>(Comparator.naturalOrder());
                    break;
                case "heap":
                    strat = new HeapSort<T>(Comparator.naturalOrder());
                    break;
            }

            Instant start, end;

            if(strat != null) {
                start = Instant.now();
                if(!sorted){
                    if(attribute != null) sortedObjectData = strat.sort(jsonData, attribute);
                    if(attribute == null) sortedData = strat.sort(data);
                    sorted = true;
                } else {
                    if(attribute != null) strat.sort(jsonData, attribute);
                    if(attribute == null) strat.sort(data);
                }
                end = Instant.now();
                times.add(Duration.between(start, end).toMillis());
            } else {
                times.add((long) -1);
            }
            strat = null;
        }
        logger.debug("[DEBUG] Sorted array: {}", data);
        logger.debug("[DEBUG] Times array: {}", times);
    }

}
