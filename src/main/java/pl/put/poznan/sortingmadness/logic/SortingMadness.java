package pl.put.poznan.sortingmadness.logic;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class SortingMadness {

    private List<SortingStrategy> sortStrat = new ArrayList<SortingStrategy>();
    private ObjectMapper mapper = new ObjectMapper();
    private boolean descending = false;
    private int iterations;
    //private Array<>

    public void addStrategy(SortingStrategy strat){
        sortStrat.add(strat);
    }


    public void readOneDimArray(){
        System.out.println("Please enter array to sort: (Example input: [4, 8, 2]");
        //TODO PARSE JSON ARRAY AND STORE IN A MEMBER VARIABLE
        //TODO THROW ERROR WHEN INPUT IS EMPTY OR WRONGLY FORMATTED
    }

    public void readObjectArray(){
        //TODO PARSE JSON OBJECT ARRAY AND STORE IN A MEMBER VARIABLE
        //TODO ASK FOR ATTRIBUTE TO SORT BY
        //TODO THROW ERROR WHEN INPUT IS EMPTY OR WRONGLY FORMATTED
        //TODO THROW ERROR WHEN ATRIBUTE DOES NOT EXIST
        //TODO THROW ERROR WHEN OBJECTS IN THE ARRAY ARE NOT OF THE SAME TYPE
    }

    public void readAlgorithm(){
        //TODO READ STRATEGIES FROM INPUT AND
        //TODO USING addStrategy ADD STRATEGY OBJECTS (E.G. addStrategy(new BubbleSort(args))
        //TODO args WILL BE THE OBJECT LIST, ORDER AND ITERATIONS
        //TODO ALTERNATIVELY SEND THE ARGS LATER DIRECTLY WHEN CALLING THE .sort() METHOD

    }

    public void readOrder(){
        //TODO READ FROM INPUT AND SET descending TO TRUE IF NEEDED
    }

    public void readIterations(){
        //TODO READ FROM INPUT HOW MANY ITERATIONS TO EXECUTE AND STORE IN iterations
        //TODO IF -1 THEN SORT ALL
    }


    public void sort() {
        //TODO ITERATE OVER LIST OF SORTING STARTS AND CALL sort(to) METHOD ON EACH OF THEM
        //TODO PRINT OUT SORTED ARRAY AND SORTING TIMES WITH THE ALGORITHM NAME
        //TODO (ADD WORD AUTO IN PARENTHESIS IF ALGORITHM WAS PICKED AUTOMATICALLY)
    }
}
