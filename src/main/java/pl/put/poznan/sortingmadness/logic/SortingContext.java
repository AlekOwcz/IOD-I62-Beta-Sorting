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
 * SortingContext class takes care of creating instances of SortingStrategy, calling them, measuring time and storing resulting Lists.
 *
 * For one dimensional arrays use data and sortedData fields.
 * To access them use setData() and getSortedData() methods.
 *
 * For JSON Objects use jsonData and sortedObjectData fields.
 * To access them use setJsonData() and getSortedJsonData() methods.
 *
 * The sorting order can be set through setOrder() method.
 */
public class SortingContext<T extends  Comparable<T>> {

    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);
    private ArrayList<T> data;
    private ArrayList<JSONObject> jsonData = null;
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

    /**
     * The sort() method is the main method responsible for calling the sort function for each algorithm in algorithms list (provided through setAlgorithms() method)
     * The measured times are stored in times ArrayList as Long values.
     */
    public void sort() {
        logger.info("[INFO] Start sorting method");
        boolean sorted = false;
        SortingStrategy<T> strat = null;
        logger.info("[INFO] Creating objects");
        Comparator<T> comparator;
        Comparator<Double> numComp;
        Comparator<String> strComp;
        logger.info("[DEBUG] Sorting order: {}", order);
        if(order.equals("reverse")) {
            comparator = Comparator.reverseOrder();
            numComp = Comparator.reverseOrder();
            strComp = Comparator.reverseOrder();
        } else {
            comparator = Comparator.naturalOrder();
            numComp = Comparator.naturalOrder();
            strComp = Comparator.naturalOrder();
        }
        int compType = 0;
        if(jsonData != null){
            if(jsonData.get(0).get(attribute) instanceof String) compType = 1;
            else compType = 2;
        }
        for(Object alg : algorithms) {
            switch(alg.toString().toLowerCase()){
                case "bubble":
                    logger.debug("[DEBUG] {} matched to bubble", alg.toString().toLowerCase());
                    if(compType == 0) strat = new BubbleSort<>(comparator);
                    if(compType == 1) strat = new BubbleSort<>(strComp, 'a');
                    if(compType == 2) strat = new BubbleSort<>(numComp, 1);
                    break;
                case "insertion":
                    logger.debug("[DEBUG] {} matched to insertion", alg.toString().toLowerCase());
                    if(compType == 0) strat = new InsertionSort<>(comparator);
                    if(compType == 1) strat = new InsertionSort<>(strComp, 'a');
                    if(compType == 2) strat = new InsertionSort<>(numComp, 1);
                    break;
                case "selection":
                    logger.debug("[DEBUG] {} matched to selection", alg.toString().toLowerCase());
                    if(compType == 0) strat = new SelectionSort<>(comparator);
                    if(compType == 1) strat = new SelectionSort<>(strComp, 'a');
                    if(compType == 2) strat = new SelectionSort<>(numComp, 1);
                    break;
                case "quick":
                    logger.debug("[DEBUG] {} matched to quick", alg.toString().toLowerCase());
                    if(compType == 0) strat = new QuickSort<>(comparator);
                    if(compType == 1) strat = new QuickSort<>(strComp, 'a');
                    if(compType == 2) strat = new QuickSort<>(numComp, 1);
                    break;
                case "merge":
                    logger.debug("[DEBUG] {} matched to merge", alg.toString().toLowerCase());
                    if(compType == 0) strat = new MergeSort<>(comparator);
                    if(compType == 1) strat = new MergeSort<>(strComp, 'a');
                    if(compType == 2) strat = new MergeSort<>(numComp, 1);
                    break;
                case "heap":
                    logger.debug("[DEBUG] {} matched to heap", alg.toString().toLowerCase());
                    if(compType == 0) strat = new HeapSort<>(comparator);
                    if(compType == 1) strat = new HeapSort<>(strComp, 'a');
                    if(compType == 2) strat = new HeapSort<>(numComp, 1);
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
