package pl.put.poznan.sortingmadness.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.w3c.dom.ls.LSInput;
import pl.put.poznan.sortingmadness.logic.SortingMadness;

import java.util.Scanner;

@SpringBootApplication(scanBasePackages = {"pl.put.poznan.sortingmadness.rest"})
public class SortingMadnessApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SortingMadnessApplication.class, args);
    }

    @Override
    public void run(String... args) {
        SortingMadness Sorter = new SortingMadness();
        Scanner myInput = new Scanner(System.in);

        System.out.println("\n\n\nWelcome to Sorting Madness!");
        System.out.println("What would you like to do?");
        System.out.println("1. Sort a one dimensional array");
        System.out.println("2. Sort an array of objects");
        System.out.println(">");

        String sortObject = myInput.nextLine();
        switch(sortObject){
            case "1":
                Sorter.readOneDimArray();
                break;
            case "2":
                Sorter.readObjectArray();
                break;
            default:
                System.out.println("Unknown operation, try again.\n>");
        }

        System.out.println("Would you like to sort in ascending or descending order?\n>");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        Sorter.readOrder();

        System.out.println("After how many iterations should the algorithm stop? (-1 to sort till the end)\n>");
        Sorter.readIterations();

        System.out.println("Which algorithms out of these do you want to use? (Example input 2, 4, 5)");
        System.out.println("1. AUTO (Picks the best algorithm to sort the data with)");
        System.out.println("2. INSERTION SORT");
        System.out.println("3. SELECTION SORT");
        System.out.println("4. BUBBLE SORT");
        System.out.println("5. QUICK SORT");
        System.out.println("6. MERGE SORT");
        System.out.println("7. HEAP SORT");
        System.out.println(">");
        Sorter.readAlgorithm();

        Sorter.sort();

    }
}
