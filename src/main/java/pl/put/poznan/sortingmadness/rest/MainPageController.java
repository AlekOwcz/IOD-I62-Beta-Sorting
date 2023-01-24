package pl.put.poznan.sortingmadness.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.put.poznan.sortingmadness.logic.FormInput;
import pl.put.poznan.sortingmadness.logic.SortingContext;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/sort")
public class MainPageController {
    @GetMapping
    public String sortForm(Model model){
        model.addAttribute("formInput", new FormInput());
        return "index";
    }
    @PostMapping
    public String sortSubmit(@Valid @ModelAttribute FormInput formInput, Errors errors, Model model){
        if(errors.hasErrors()) {
            return "index";
        }
        String type = formInput.getType();
        switch (type) {
            case "text": {
                ArrayList<String> listText = new ArrayList<>(Arrays.asList(formInput.getData().split("\n")));
                SortingContext<String> sortingContext = new SortingContext<>();
                sortingContext.setData(listText);
                setResult(model, formInput, sortingContext);
                break;
            }
            case "number": {
                double[] array = Arrays.stream(formInput.getData().split("\n")).mapToDouble(Double::parseDouble).toArray();
                ArrayList<Double> listNumber = new ArrayList<>();
                for (double item : array) {
                    listNumber.add(item);
                }
                SortingContext<Double> sortingContext = new SortingContext<>();
                sortingContext.setData(listNumber);
                setResult(model, formInput, sortingContext);
                break;
            }
            case "jsonNumber": {
                SortingContext<Double> sortingContext = new SortingContext<>();
                setResultJSON(model, formInput,sortingContext);
                break;
            }
            case "jsonText": {
                SortingContext<String> sortingContext = new SortingContext<>();
                setResultJSON(model, formInput, sortingContext);
            }
        }
        return "result";
    }

    private <T extends Comparable<T>> void setResult(Model model, FormInput formInput, SortingContext<T> sortingContext) {
        JSONArray methods = new JSONArray(formInput.getMethods());
        sortingContext.setAlgorithms(methods);
        sortingContext.setOrder(formInput.getOrder());
        sortingContext.sort();
        ArrayList<T> sortedData = sortingContext.getSortedData();
        addTimes(model, formInput, sortingContext, sortedData);
    }
    private <T extends Comparable<T>, U> void addTimes(Model model, FormInput formInput, SortingContext<T> sortingContext, ArrayList<U> sortedData ) {
        JSONArray methods = new JSONArray(formInput.getMethods());
        var times = sortingContext.getTimes();
        model.addAttribute("sortedData", sortedData.toString());
        ArrayList<String> selectedMethods = new ArrayList<>();
        for (int i = 0; i < methods.length(); i++) {
            selectedMethods.add(formInput.getMethods().get(i) + " : " + times.get(i) + " [s]");
        }
        model.addAttribute("selectedMethods", selectedMethods);
    }
   private ArrayList<JSONObject> loadJSON(FormInput formInput) {
       String[] objects = formInput.getData().split("\n");
       ArrayList<JSONObject> jsonObjects = new ArrayList<>();
       for (String object : objects) {
           String[] attributes = object.split(" ");
           JSONObject object1 = new JSONObject();
           for (String attribute : attributes) {
               String[] pair = attribute.split(":");
               object1.put(pair[0].replaceAll("(\\r|\\n)", ""), pair[1].replaceAll("(\\r|\\n)", ""));
           }
           jsonObjects.add(object1);
       }
       return jsonObjects;
   }

   private <T extends Comparable<T>> void setResultJSON(Model model, FormInput formInput, SortingContext<T> sortingContext){
       ArrayList<JSONObject> jsonObjects = loadJSON(formInput);
       String attr = formInput.getAttribute();
       sortingContext.setJsonData(jsonObjects);
       sortingContext.setAttribute(attr);
       sortingContext.setAlgorithms(new JSONArray(formInput.getMethods()));
       sortingContext.setOrder(formInput.getOrder());
       sortingContext.sort();
       var sortedData = sortingContext.getSortedJsonData();
       addTimes(model, formInput, sortingContext, sortedData);
   }
}
