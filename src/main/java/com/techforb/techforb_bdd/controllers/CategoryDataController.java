package com.techforb.techforb_bdd.controllers;
import com.techforb.techforb_bdd.models.CategoryData;
import com.techforb.techforb_bdd.services.CategoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/category-data")
public class CategoryDataController {

    @Autowired
    private CategoryDataService categoryDataService;

    @CrossOrigin(origins = "*")
    @PutMapping("/update-category/{id}")
    public ResponseEntity<?> updateCategoryData(@PathVariable Long id, @RequestBody CategoryData updatedData) {
        Optional<CategoryData> existingData = categoryDataService.getById(id);

        if (existingData.isPresent()) {
            CategoryData categoryData = existingData.get();
            categoryData.setReadings(updatedData.getReadings());
            categoryData.setMediumAlerts(updatedData.getMediumAlerts());
            categoryData.setRedAlerts(updatedData.getRedAlerts());
            categoryData.setDisabledSensors(updatedData.getDisabledSensors());

            CategoryData savedData = categoryDataService.save(categoryData);
            return new ResponseEntity<>(savedData, HttpStatus.OK);
        }

        return new ResponseEntity<>("CategoryData not found", HttpStatus.NOT_FOUND);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCategoryDataById(@PathVariable Long id) {
        Optional<CategoryData> categoryData = categoryDataService.getById(id);

        if (categoryData.isPresent()) {
            return new ResponseEntity<>(categoryData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("CategoryData not found", HttpStatus.NOT_FOUND);
    }





}
