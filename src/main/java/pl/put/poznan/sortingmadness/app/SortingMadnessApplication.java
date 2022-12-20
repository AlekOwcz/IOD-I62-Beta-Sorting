package pl.put.poznan.sortingmadness.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.w3c.dom.ls.LSInput;
import pl.put.poznan.sortingmadness.logic.MergeSort;
import pl.put.poznan.sortingmadness.logic.SortingMadness;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = {"pl.put.poznan.sortingmadness.rest"})
public class SortingMadnessApplication {

    public static void main(String[] args) {
//        MergeSort<Integer> m = new MergeSort<Integer>(Comparator.naturalOrder());
//        ArrayList<Integer> data = new ArrayList<>(List.of(4, 3, 1, 9, 2, 5, 7));
//        ArrayList<Integer> result = m.sort(data);
//        for (Integer x: result) {
//            System.out.println(x);
//        }
//        MergeSort<Integer> m1 = new MergeSort<Integer>(Comparator.reverseOrder());
//        result = m1.sort(data);
//        for (Integer x: result) {
//            System.out.println(x);
//        }
        SpringApplication.run(SortingMadnessApplication.class, args);
    }
}
