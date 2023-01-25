package pl.put.poznan.sortingmadness.logic;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BubbleSortTest {
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
        BubbleSort<Double> bubbleSorter = new BubbleSort<Double>(cmp);
        ArrayList<Double> result = null;
        result = bubbleSorter.sort(data);
        ArrayList<Double> expected = new ArrayList<Double>(Arrays.asList(12.03, 5.0, 2.0, 1.0));
        assertEquals(expected, result);
    }

    @Test
    void testForwardNumericSort() {
        Comparator<Double> cmp = Comparator.naturalOrder();
        BubbleSort<Double> bubbleSorter = new BubbleSort<Double>(cmp);
        ArrayList<Double> result = null;
        result = bubbleSorter.sort(data);
        ArrayList<Double> expected = new ArrayList<Double>(Arrays.asList(1.0, 2.0, 5.0, 12.03));
        assertEquals(expected, result);
    }

    @Test
    void testForwardStringSort() {
        Comparator<String> cmp = Comparator.naturalOrder();
        BubbleSort<String> bubbleSorter = new BubbleSort<String>(cmp);
        ArrayList<String> result = null;
        result = bubbleSorter.sort(dataString);
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("bar","cat","dog","foo"));
        assertEquals(expected, result);
    }

    @Test
    void testReverseStringSort() {
        Comparator<String> cmp = Comparator.reverseOrder();
        BubbleSort<String> bubbleSorter = new BubbleSort<String>(cmp);
        ArrayList<String> result = null;
        result = bubbleSorter.sort(dataString);
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("foo","dog","cat","bar"));
        assertEquals(expected, result);
    }

    @Test
    void testBubbleComparisons(){
        Comparator<String> cmp = mock(Comparator.class);
        BubbleSort<String> bubbleSorter = new BubbleSort<String>(cmp);
        ArrayList<String> result = null;
        result = bubbleSorter.sort(dataString);
        verify(cmp, times(6)).compare(Mockito.anyString(), Mockito.anyString());
    }
}