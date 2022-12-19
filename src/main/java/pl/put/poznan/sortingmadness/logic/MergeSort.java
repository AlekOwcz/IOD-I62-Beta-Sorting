package pl.put.poznan.sortingmadness.logic;

import java.util.Arrays;

public class MergeSort implements SortingStrategy {

    static public void sortInt() {
        int[] data = {4, 3, 1, 9, 2, 5, 7};
        int[] result = mergeSort(data);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    static int[] mergeSort(int[] data){
        if (data.length > 1) {
            int[] left = mergeSort(Arrays.copyOfRange(data, 0, data.length / 2));
            int[] right = mergeSort(Arrays.copyOfRange(data, data.length / 2, data.length));
            int[] result = new int[data.length];
            int i = 0;
            int j = 0;
            int index = 0;
            while (i < left.length || j < right.length) {
                if ( i >= left.length) {
                    result[index] = right[j];
                    j++;
                } else if (j >= right.length) {
                    result[index] = left[i];
                    i++;
                } else if (left[i] < right[j]) {
                    result[index] = left[i];
                    i++;
                } else {
                    result[index] = right[j];
                    j++;
                }
                index++;
            }
            return result;
        } else
        {
            return  data;
        }
    }
}
