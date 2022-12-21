package pl.put.poznan.sortingmadness.logic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

public interface SortingStrategy<T extends Comparable<T>>{
    ArrayList<T> sort(ArrayList<T> data);
    ArrayList<T> sort(ArrayList<T> data, String attribute);
}
