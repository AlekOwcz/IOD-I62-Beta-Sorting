package pl.put.poznan.sortingmadness.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.*;
import pl.put.poznan.sortingmadness.logic.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


@RestController
@RequestMapping("/api/")
public class SortingMadnessController {

    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);

    @RequestMapping(value="/array/",method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> post(@RequestBody JSONInputObj json) {
        JSONObject jsonObj = new JSONObject(json);
        try {
            if (((JSONArray) jsonObj.get("algorithms")).length() == 0) {
                logger.error("[ERROR] No algorithms selected");
                return new ResponseEntity<>("[ERROR] You must pick at least one algorithm", HttpStatus.BAD_REQUEST);
            }
        } catch (JSONException e) {
            logger.error("[ERROR] Algorithms field missing or not Array");
            return new ResponseEntity<>("[ERROR] Missing algorithms field or field not of array type", HttpStatus.BAD_REQUEST);
        }
        try {
            if (((JSONArray) jsonObj.get("data")).length() == 0) {
                logger.error("[ERROR] Data empty");
                return new ResponseEntity<>("[ERROR] Data can not be empty", HttpStatus.BAD_REQUEST);
            }
        } catch (JSONException e) {
            logger.error("[ERROR] Data field missing or not Array");
            return new ResponseEntity<>("[ERROR] Missing data field or field not of array type", HttpStatus.BAD_REQUEST);
        }

        JSONArray data = jsonObj.getJSONArray("data");

        logger.info("[INFO] Algorithms: " + json.getAlgorithms().toString());
        logger.info("[INFO] Json data: " + json.getData().toString());

        ArrayList<Integer> numberData = new ArrayList<>();
        ArrayList<String> stringData = new ArrayList<>();
        boolean ints = false, strings = false;
        for(int i = 0; i < data.length(); i++){
            Object el = data.get(i);
            if (el instanceof Integer){
                if (!ints) ints = true;
                numberData.add(Integer.parseInt(el.toString()));
            }
            if (el instanceof String){
                if (!strings) strings = true;
                stringData.add(el.toString());
            }
            if(ints && strings) {
                logger.error("[ERROR] Array element type mismatch");
                return new ResponseEntity<>("[ERROR] Attribute types must match", HttpStatus.BAD_REQUEST);
            }
        }
        logger.info("[INFO] Parsed Integer array: {}", (Object) numberData);
        logger.info("[INFO] Parsed String array: {}", (Object) stringData);
        int numberOfAlgorithms = ((JSONArray) jsonObj.get("algorithms")).length();
        long[] times = new long[numberOfAlgorithms];
        int measurement = 0;

        if(ints) {
            ArrayList<Integer> sortedData = null;
            boolean sorted = false;
            SortingStrategy<Integer> strat = null;
            for(Object alg : jsonObj.getJSONArray("algorithms")) {
                System.out.println(alg.toString());
                switch(alg.toString().toLowerCase()){
                    case "bubble":
                        strat = new BubbleSort<Integer>(Comparator.naturalOrder());;
                        break;
                    case "insertion":
                        strat = new InsertionSort<Integer>(Comparator.naturalOrder());
                        break;
                    case "selection":
                        strat = new SelectionSort<Integer>(Comparator.naturalOrder());
                        break;
                    case "quick":
                        strat = new QuickSort<Integer>(Comparator.naturalOrder());
                        break;
                    case "merge":
                        strat = new MergeSort<Integer>(Comparator.naturalOrder());
                        break;
                    case "heap":
                        strat = new HeapSort<Integer>(Comparator.naturalOrder());
                        break;
                }

                Instant start, end;

                if(strat != null) {
                    start = Instant.now();
                    if(!sorted){
                        sortedData = strat.sort(numberData);
                        sorted = true;
                    } else {
                        strat.sort(numberData);
                    }
                    end = Instant.now();
                    times[measurement++] = Duration.between(start, end).toMillis();
                } else {
                    times[measurement++] = -1;
                }
                strat = null;
            }
            JSONOutputObj sortedObj = new JSONOutputObj(json.getAlgorithms(),sortedData,json.getAttribute(),times);
            JSONObject returnObject = new JSONObject(sortedObj);
            return new ResponseEntity<>(returnObject.toString(), HttpStatus.OK);
        }
        if(strings){
            ArrayList<String> sortedData = null;
            boolean sorted = false;
            SortingStrategy<String> strat = null;
            for(Object alg : jsonObj.getJSONArray("algorithms")) {
                System.out.println(alg.toString());
                switch(alg.toString().toLowerCase()){
                    case "bubble":
                        strat = new BubbleSort<String>(Comparator.naturalOrder());;
                        break;
                    case "insertion":
                        strat = new InsertionSort<String>(Comparator.naturalOrder());
                        break;
                    case "selection":
                        strat = new SelectionSort<String>(Comparator.naturalOrder());
                        break;
                    case "quick":
                        strat = new QuickSort<String>(Comparator.naturalOrder());
                        break;
                    case "merge":
                        strat = new MergeSort<String>(Comparator.naturalOrder());
                        break;
                    case "heap":
                        strat = new HeapSort<String>(Comparator.naturalOrder());
                        break;
                }

                Instant start, end;

                if(strat != null) {
                    start = Instant.now();
                    if(!sorted){
                        sortedData = strat.sort(stringData);
                        logger.info("[INFO] Sorted data: {}", sortedData);
                        sorted = true;
                    } else {
                        strat.sort(stringData);
                    }
                    end = Instant.now();
                    long sortingTime = Duration.between(start, end).toMillis();
                    logger.info("[INFO] Sorting time: {}", sortingTime);
                    times[measurement++] = sortingTime;
                } else {
                    times[measurement++] = -1;
                }
                strat = null;
            }
            JSONOutputObj sortedObj = new JSONOutputObj(json.getAlgorithms(),sortedData,json.getAttribute(),times);
            JSONObject returnObject = new JSONObject(sortedObj);
            return new ResponseEntity<>(returnObject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR: Unknown error", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/object/",method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> postObj(@RequestBody JSONInputObj json) {
        JSONObject jsonObj = new JSONObject(json);
        try {
            if (((JSONArray) jsonObj.get("algorithms")).length() == 0) {
                logger.error("[ERROR] No algorithms selected");
                return new ResponseEntity<>("[ERROR] You must pick at least one algorithm", HttpStatus.BAD_REQUEST);
            }
        } catch (JSONException e) {
            logger.error("[ERROR] Algorithms field missing or not Array");
            return new ResponseEntity<>("[ERROR] Missing algorithms field or field not of array type", HttpStatus.BAD_REQUEST);
        }
        try {
            if (((JSONArray) jsonObj.get("data")).length() == 0) {
                logger.error("[ERROR] Data empty");
                return new ResponseEntity<>("[ERROR] Data can not be empty", HttpStatus.BAD_REQUEST);
            }
        } catch (JSONException e) {
            logger.error("[ERROR] Data field missing or not Array");
            return new ResponseEntity<>("[ERROR] Missing data field or field not of array type", HttpStatus.BAD_REQUEST);
        }
        String attr = jsonObj.get("attribute").toString();
        JSONArray data = jsonObj.getJSONArray("data");

        logger.info("[INFO] Algorithms: " + json.getAlgorithms().toString());
        logger.info("[INFO] Json data: " + json.getData().toString());
        logger.info("[INFO] Object attribute: " + json.getAttribute());

        ArrayList<Integer> numberData = new ArrayList<>();
        ArrayList<String> stringData = new ArrayList<>();
        boolean ints = false, strings = false;
        Object el = "NULL";
        for(int i = 0; i < data.length(); i++){
            try {
                el = data.getJSONObject(i).get(attr);
                if (el instanceof Integer){
                    if (!ints) ints = true;
                    numberData.add(Integer.parseInt(el.toString()));
                }
                if (el instanceof String){
                    if (!strings) strings = true;
                    stringData.add(el.toString());
                }
                if(ints && strings) {
                    logger.error("[ERROR] Attribute type mismatch");
                    return new ResponseEntity<>("[ERROR] Attribute types must match", HttpStatus.BAD_REQUEST);
                }
            } catch (JSONException e) {
                logger.error("[ERROR] Object {} lacks attribute {}", el.toString(), attr);
                return new ResponseEntity<>("[ERROR] At least one object does not posses attribute " + attr, HttpStatus.BAD_REQUEST);
            }
        }
        logger.info("[INFO] Parsed Integer array: {}", numberData);
        logger.info("[INFO] Parsed String array: {}", stringData);
        int numberOfAlgorithms = ((JSONArray) jsonObj.get("algorithms")).length();
        long[] times = new long[numberOfAlgorithms];
        int measurement = 0;

        if(ints) {
            ArrayList<Integer> sortedData = null;
            boolean sorted = false;
            SortingStrategy<Integer> strat = null;
            for(Object alg : jsonObj.getJSONArray("algorithms")) {
                System.out.println(alg.toString());
                switch(alg.toString().toLowerCase()){
                    case "bubble":
                        strat = new BubbleSort<Integer>(Comparator.naturalOrder());;
                        break;
                    case "insertion":
                        strat = new InsertionSort<Integer>(Comparator.naturalOrder());
                        break;
                    case "selection":
                        strat = new SelectionSort<Integer>(Comparator.naturalOrder());
                        break;
                    case "quick":
                        strat = new QuickSort<Integer>(Comparator.naturalOrder());
                        break;
                    case "merge":
                        strat = new MergeSort<Integer>(Comparator.naturalOrder());
                        break;
                    case "heap":
                        strat = new HeapSort<Integer>(Comparator.naturalOrder());
                        break;
                }

                Instant start, end;

                if(strat != null) {
                    start = Instant.now();
                    if(!sorted){
                        sortedData = strat.sort(numberData);
                        sorted = true;
                    } else {
                        strat.sort(numberData);
                    }
                    end = Instant.now();
                    times[measurement++] = Duration.between(start, end).toMillis();
                } else {
                    times[measurement++] = -1;
                }
                strat = null;
            }
            JSONOutputObj sortedObj = new JSONOutputObj(json.getAlgorithms(),sortedData,json.getAttribute(),times);
            JSONObject returnObject = new JSONObject(sortedObj);
            return new ResponseEntity<>(returnObject.toString(), HttpStatus.OK);
        }
        if(strings){
            ArrayList<String> sortedData = null;
            boolean sorted = false;
            SortingStrategy<String> strat = null;
            for(Object alg : jsonObj.getJSONArray("algorithms")) {
                System.out.println(alg.toString());
                switch(alg.toString().toLowerCase()){
                    case "bubble":
                        strat = new BubbleSort<String>(Comparator.naturalOrder());;
                        break;
                    case "insertion":
                        strat = new InsertionSort<String>(Comparator.naturalOrder());
                        break;
                    case "selection":
                        strat = new SelectionSort<String>(Comparator.naturalOrder());
                        break;
                    case "quick":
                        strat = new QuickSort<String>(Comparator.naturalOrder());
                        break;
                    case "merge":
                        strat = new MergeSort<String>(Comparator.naturalOrder());
                        break;
                    case "heap":
                        strat = new HeapSort<String>(Comparator.naturalOrder());
                        break;
                }

                Instant start, end;

                if(strat != null) {
                    start = Instant.now();
                    if(!sorted){
                        sortedData = strat.sort(stringData);
                        logger.info("[INFO] Sorted data: {}", sortedData);
                        sorted = true;
                    } else {
                        strat.sort(stringData);
                    }
                    end = Instant.now();
                    long sortingTime = Duration.between(start, end).toMillis();
                    logger.info("[INFO] Sorting time: {}", sortingTime);
                    times[measurement++] = sortingTime;
                } else {
                    times[measurement++] = -1;
                }
                strat = null;
            }
            JSONOutputObj sortedObj = new JSONOutputObj(json.getAlgorithms(),sortedData,json.getAttribute(),times);
            JSONObject returnObject = new JSONObject(sortedObj);
            return new ResponseEntity<>(returnObject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR: Unknown error", HttpStatus.NOT_FOUND);
    }



}


