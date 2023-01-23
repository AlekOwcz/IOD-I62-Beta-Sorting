package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Interface for implementing sorting algorithms.
 */
public interface SortingStrategy<T extends Comparable<T>>{
    /**
     * Method for calling the sorting algorithm
     * @param data - input data in the form of ArrayList of type either Double or String
     * @return ArrayList of sorted data
     */
    ArrayList<T> sort(ArrayList<T> data);

    /**
     * Method for calling the sorting algorithm on JSON objects
     * @param data - an ArrayList of JSONObject
     * @param attribute - attribute of the JSON object by which the data should be sorted
     * @return ArrayList of sorted JSONObject
     */
    ArrayList<JSONObject> sort(ArrayList<JSONObject> data, String attribute);
}
