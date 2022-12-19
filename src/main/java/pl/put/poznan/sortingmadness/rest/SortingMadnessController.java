package pl.put.poznan.sortingmadness.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.*;
import pl.put.poznan.sortingmadness.logic.SortingMadness;

import java.util.Arrays;


@RestController
@RequestMapping("/api/")
public class SortingMadnessController {

    private static final Logger logger = LoggerFactory.getLogger(SortingMadnessController.class);

    @RequestMapping(value="/object/",method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> post(@RequestBody JSONInputObj json) {
        JSONObject jsonObj = new JSONObject(json);
        if (((JSONArray) jsonObj.get("algorithms")).length() == 0) {
            logger.error("[ERROR] NO ALGORITHMS SELECTED");
            return new ResponseEntity<>("[ERROR] YOU MUST PICK AT LEAST ONE ALGORITHM", HttpStatus.BAD_REQUEST);
        }
        if (((JSONArray) jsonObj.get("data")).length() == 0) {
            logger.error("[ERROR] DATA EMPTY");
            return new ResponseEntity<>("[ERROR] DATA CAN NOT BE EMPTY", HttpStatus.BAD_REQUEST);
        }
       // System.out.println(   ((JSONArray) jsonObj.get("data")).getJSONObject(0).get("aaa")   );
        //todo check if attribute is in data
        //todo logger
        int numberOfAlgorithms = ((JSONArray) jsonObj.get("algorithms")).length();

        //for(Object alg : (JSONArray) jsonObj.get("algorithms")) {
         //
        //}
        return new ResponseEntity<>(jsonObj.toString().toUpperCase(), HttpStatus.OK);
    }



}


