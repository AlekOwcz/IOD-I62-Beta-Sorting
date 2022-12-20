package pl.put.poznan.sortingmadness.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.*;
import pl.put.poznan.sortingmadness.logic.*;

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
        SortingStrategy strat = null;
        for(Object alg : jsonObj.getJSONArray("algorithms")) {
            System.out.println(alg.toString());
            switch(alg.toString().toLowerCase()){
                case "bubble":
                    strat = new BubbleSort();
                    break;
                case "insertion":
                    strat = new InsertionSort(Comparator.naturalOrder());
                    break;
                case "selection":
                    strat = new SelectionSort(Comparator.naturalOrder());
                    break;
                case "quick":
                    strat = new QuickSort();
                    break;
                case "merge":
                    if (ints)
                        strat = new MergeSort<Integer>(Comparator.naturalOrder());
                    else
                        strat = new MergeSort<String>(Comparator.naturalOrder());
                    break;
                case "heap":
                    strat = new HeapSort();
                    break;
            }
            if (strat != null) {
                if (ints) {
                    ArrayList<Integer> result;
                    result = strat.sort(numberData);
                    for(Integer x: result) {
                        System.out.println(x);
                    }
                }
                else {
                    ArrayList<String> result;
                    result = strat.sort(stringData);
                    for(String x: result) {
                        System.out.println(x);
                    }
                }
            }
        }
        return new ResponseEntity<>(jsonObj.toString().toUpperCase(), HttpStatus.OK);
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
        Object el = null;
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
        logger.info("[INFO] Parsed Integer array: {}", (Object) numberData);
        logger.info("[INFO] Parsed String array: {}", (Object) stringData);

        int numberOfAlgorithms = ((JSONArray) jsonObj.get("algorithms")).length();
        SortingStrategy strat;
        for(Object alg : jsonObj.getJSONArray("algorithms")) {
            System.out.println(alg.toString());
            switch(alg.toString().toLowerCase()){
                case "bubble":
                    strat = new BubbleSort();
                    break;
                case "insertion":
                    strat = new InsertionSort(Comparator.naturalOrder());
                    break;
                case "selection":
                    strat = new SelectionSort(Comparator.naturalOrder());
                    break;
                case "quick":
                    strat = new QuickSort();
                    break;
                case "merge":
                    strat = new MergeSort(Comparator.naturalOrder());
                    break;
                case "heap":
                    strat = new HeapSort();
                    break;
            }

        }
        //tmp for testing
        return new ResponseEntity<>(jsonObj.toString().toUpperCase(), HttpStatus.OK);
    }



}


