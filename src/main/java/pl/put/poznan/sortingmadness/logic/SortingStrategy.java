package pl.put.poznan.sortingmadness.logic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public interface SortingStrategy<T extends Comparable<T>>{
    ArrayList<T> sort(ArrayList<T> data);
    ArrayList<JSONObject> sort(ArrayList<JSONObject> data, String attribute);
}
