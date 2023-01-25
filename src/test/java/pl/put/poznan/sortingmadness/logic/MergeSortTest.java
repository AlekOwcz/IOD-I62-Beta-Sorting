package pl.put.poznan.sortingmadness.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    ArrayList<Double> data = null;

    ArrayList<String> dataString = null;
    @BeforeEach
    void initData() {
        data = new ArrayList<Double>(Arrays.asList(5.0, 2.0, 12.03, 1.0));
        dataString = new ArrayList<String>(Arrays.asList("foo","bar","dog","cat"));
    }
    @AfterEach
    void tearDown() {
        data = null;
        dataString = null;
    }

    @Test
    void testReverseNumericSort() {
        Comparator<Double> cmp = Comparator.reverseOrder();
        MergeSort<Double> mergeSorter = new MergeSort<Double>(cmp);
        ArrayList<Double> result = null;
        result = mergeSorter.sort(data);
        ArrayList<Double> expected = new ArrayList<Double>(Arrays.asList(12.03, 5.0, 2.0, 1.0));
        assertEquals(expected, result);
    }

    @Test
    void testForwardNumericSort() {
        Comparator<Double> cmp = Comparator.naturalOrder();
        MergeSort<Double> mergeSorter = new MergeSort<Double>(cmp);
        ArrayList<Double> result = null;
        result = mergeSorter.sort(data);
        ArrayList<Double> expected = new ArrayList<Double>(Arrays.asList(1.0, 2.0, 5.0, 12.03));
        assertEquals(expected, result);
    }

    @Test
    void testForwardStringSort() {
        Comparator<String> cmp = Comparator.naturalOrder();
        MergeSort<String> mergeSorter = new MergeSort<String>(cmp);
        ArrayList<String> result = null;
        result = mergeSorter.sort(dataString);
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("bar","cat","dog","foo"));
        assertEquals(expected, result);
    }

    @Test
    void testReverseStringSort() {
        Comparator<String> cmp = Comparator.reverseOrder();
        MergeSort<String> mergeSorter = new MergeSort<String>(cmp);
        ArrayList<String> result = null;
        result = mergeSorter.sort(dataString);
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("foo","dog","cat","bar"));
        assertEquals(expected, result);
    }
}