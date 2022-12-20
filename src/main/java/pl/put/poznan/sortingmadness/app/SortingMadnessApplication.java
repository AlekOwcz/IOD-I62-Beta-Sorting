package pl.put.poznan.sortingmadness.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.sortingmadness.logic.QuickSort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"pl.put.poznan.sortingmadness.rest"})
public class SortingMadnessApplication {

    public static void main(String[] args) {
        QuickSort<Integer> s1 = new QuickSort<Integer>(Comparator.naturalOrder());
        ArrayList<Integer> data = new ArrayList<>(List.of(4, 3, 1, 9, 2, 5, 7));
        ArrayList<Integer> result = s1.sort(data);
        for (Integer x: result) {
            System.out.println(x);
        }
        System.out.println();
        QuickSort<Integer> s2 = new QuickSort<Integer>(Comparator.reverseOrder());
        result = s2.sort(data);
        for (Integer x: result) {
            System.out.println(x);
        }
        SpringApplication.run(SortingMadnessApplication.class, args);
    }
}
