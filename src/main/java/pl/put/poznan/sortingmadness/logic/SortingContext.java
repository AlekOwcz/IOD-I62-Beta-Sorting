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

/**
 * SortingContext class takes care of creating instances of SortingStrategy, calling them, measuring time and storing resulting Lists
 * For one dimensional arrays use data and sortedData fields and set them using
 *
 */
public class SortingContext<T extends  Comparable<T>> {

    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);
    private ArrayList<T> data;
    private ArrayList<JSONObject> jsonData;
    private final ArrayList<Long> times = new ArrayList<>();
    private JSONArray algorithms;
    private ArrayList<T> sortedData = null;
    private ArrayList<JSONObject> sortedObjectData = null;
    private String attribute = null;
    private String order = "natural";

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
    public void setOrder(String order){
        this.order = order;
    }
    public ArrayList<T> getSortedData() { return sortedData; }
    public ArrayList<JSONObject> getSortedJsonData() { return sortedObjectData; }
    public ArrayList<Long> getTimes() { return times; }


    public void sort() {
        logger.info("[INFO] Start sorting method");
        boolean sorted = false;
        SortingStrategy<T> strat = null;
        logger.info("[INFO] Creating objects");
        Comparator<T> comparator;
        if(order.equals("natural")) {
            comparator = Comparator.naturalOrder();
        } else {
            comparator = Comparator.reverseOrder();
        }
        for(Object alg : algorithms) {
            switch(alg.toString().toLowerCase()){
                case "bubble":
                    logger.debug("[DEBUG] {} matched to bubble", alg.toString().toLowerCase());
                    strat = new BubbleSort<>(comparator);
                    break;
                case "insertion":
                    logger.debug("[DEBUG] {} matched to insertion", alg.toString().toLowerCase());
                    strat = new InsertionSort<>(comparator);
                    break;
                case "selection":
                    logger.debug("[DEBUG] {} matched to selection", alg.toString().toLowerCase());
                    strat = new SelectionSort<>(comparator);
                    break;
                case "quick":
                    logger.debug("[DEBUG] {} matched to quick", alg.toString().toLowerCase());
                    strat = new QuickSort<>(comparator);
                    break;
                case "merge":
                    logger.debug("[DEBUG] {} matched to merge", alg.toString().toLowerCase());
                    strat = new MergeSort<>(comparator);
                    break;
                case "heap":
                    logger.debug("[DEBUG] {} matched to heap", alg.toString().toLowerCase());
                    strat = new HeapSort<>(comparator);
                    break;
            }

            Instant start, end;

            if(strat != null) {
                logger.info("[INFO] Running sorting algorithm");
                logger.info("[INFO] Starting timer");
                start = Instant.now();
                if(!sorted){
                    if(attribute != null) sortedObjectData = strat.sort(jsonData, attribute);
                    if(attribute == null) sortedData = strat.sort(data);
                    sorted = true;
                } else {
                    if(attribute != null) strat.sort(jsonData, attribute);
                    if(attribute == null) strat.sort(data);
                }
                logger.info("[INFO] Stopping timer");
                end = Instant.now();
                logger.info("[INFO] Measured time [ms]: {}", Duration.between(start, end).toMillis());
                times.add(Duration.between(start, end).toMillis());
            } else {
                logger.info("[INFO] Unknown algorithm - setting time to -1");
                times.add((long) -1);
            }
            strat = null;
        }
        logger.debug("[DEBUG] Sorted array: {}", data);
        logger.debug("[DEBUG] Times array: {}", times);
    }

}
