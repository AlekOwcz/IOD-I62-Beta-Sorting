package pl.put.poznan.sortingmadness.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.*;
import pl.put.poznan.sortingmadness.logic.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/api/")
public class SortingMadnessController {

    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);

    @RequestMapping(value="/array/",method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> post(@RequestBody JSONInputObj json) {
        JSONObject jsonObj = new JSONObject(json);
        String order = "natural";
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

        try {
            order = jsonObj.get("order").toString();
            logger.info("[DEBUG] Order field value: {}", order);
        } catch (JSONException ignored) {
            logger.info("[DEBUG] Failed to read order field");
        }

        JSONArray data = jsonObj.getJSONArray("data");

        logger.debug("[DEBUG] Algorithms: " + json.getAlgorithms().toString());
        logger.debug("[DEBUG] Json data: " + json.getData().toString());

        ArrayList<Double> numberData = new ArrayList<>();
        ArrayList<String> stringData = new ArrayList<>();
        boolean ints = false, strings = false;
        for(int i = 0; i < data.length(); i++){
            Object el = data.get(i);
            if (el instanceof Integer || el instanceof Float || el instanceof Double){
                if (!ints) ints = true;
                numberData.add(Double.parseDouble(el.toString()));
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
        logger.debug("[DEBUG] Parsed Number array: {}", numberData);
        logger.debug("[DEBUG] Parsed String array: {}", stringData);
        if(ints) {
            SortingContext<Double> sortContext = new SortingContext<>();
            sortContext.setData(numberData);
            sortContext.setAlgorithms(jsonObj.getJSONArray("algorithms"));
            sortContext.setOrder(order);
            sortContext.sort();
            JSONOutputObj sortedObj = new JSONOutputObj(json.getAlgorithms(),
                    sortContext.getSortedData(),
                    json.getAttribute(),
                    sortContext.getTimes());
            JSONObject returnObject = new JSONObject(sortedObj);
            return new ResponseEntity<>(returnObject.toString(), HttpStatus.OK);
        }
        if(strings){
            SortingContext<String> sortContext = new SortingContext<>();
            sortContext.setData(stringData);
            sortContext.setAlgorithms(jsonObj.getJSONArray("algorithms"));
            sortContext.setOrder(order);
            sortContext.sort();
            JSONOutputObj sortedObj = new JSONOutputObj(json.getAlgorithms(),
                    sortContext.getSortedData(),
                    json.getAttribute(),
                    sortContext.getTimes());
            JSONObject returnObject = new JSONObject(sortedObj);
            return new ResponseEntity<>(returnObject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR: Unknown error", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/object/",method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> postObj(@RequestBody JSONInputObj json) {
        JSONObject jsonObj = new JSONObject(json);
        String order = "natural";
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

        try {
            order = jsonObj.get("order").toString();
            logger.info("[DEBUG] Order field value: {}", order);
        } catch (JSONException ignored) {
            logger.info("[DEBUG] Failed to read order field");
        }

        String attr = jsonObj.get("attribute").toString();
        JSONArray data = jsonObj.getJSONArray("data");

        logger.debug("[DEBUG] Algorithms: " + json.getAlgorithms().toString());
        logger.debug("[DEBUG] Json data: " + json.getData().toString());
        logger.debug("[DEBUG] Object attribute: " + json.getAttribute());

        ArrayList<JSONObject> objectData = new ArrayList<>();
        boolean ints = false, strings = false;
        Object el = "NULL";
        for(int i = 0; i < data.length(); i++){
            try {
                objectData.add(data.getJSONObject(i));
                el = (objectData.get(i)).get(attr);
                if (el instanceof Integer || el instanceof Float || el instanceof Double){
                    if (!ints) ints = true;
                }
                if (el instanceof String){
                    if (!strings) strings = true;
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
        logger.debug("[DEBUG] Object array: {}", objectData);
        if(ints) {
            SortingContext<Double> sortContext = new SortingContext<>();
            sortContext.setJsonData(objectData);
            sortContext.setAlgorithms(jsonObj.getJSONArray("algorithms"));
            sortContext.setAttribute(attr);
            sortContext.setOrder(order);
            sortContext.sort();
            JSONOutputObj sortedObj = new JSONOutputObj(json.getAlgorithms(),
                                                        sortContext.getSortedJsonData(),
                                                        json.getAttribute(),
                                                        sortContext.getTimes());
            JSONObject returnObject = new JSONObject(sortedObj);
            return new ResponseEntity<>(returnObject.toString(), HttpStatus.OK);
        }
        if(strings){
            SortingContext<String> sortContext = new SortingContext<>();
            sortContext.setJsonData(objectData);
            sortContext.setAlgorithms(jsonObj.getJSONArray("algorithms"));
            sortContext.setAttribute(attr);
            sortContext.setOrder(order);
            sortContext.sort();
            JSONOutputObj sortedObj = new JSONOutputObj(json.getAlgorithms(),
                    sortContext.getSortedJsonData(),
                    json.getAttribute(),
                    sortContext.getTimes());
            JSONObject returnObject = new JSONObject(sortedObj);
            return new ResponseEntity<>(returnObject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("ERROR: Unknown error", HttpStatus.NOT_FOUND);
    }



}


