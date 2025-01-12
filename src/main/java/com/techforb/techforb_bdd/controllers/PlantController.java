package com.techforb.techforb_bdd.controllers;

import com.techforb.techforb_bdd.models.Plant;
import com.techforb.techforb_bdd.models.User;
import com.techforb.techforb_bdd.services.PlantService;
import com.techforb.techforb_bdd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;
    @Autowired
    private UserService userService;
    @CrossOrigin(origins = "*")
    @PostMapping(path = "/create-plant")
    public ResponseEntity<?> createPlant(@RequestBody Plant plant) {

        if (plant.getUser() == null || plant.getUser().getId() == null) {
            return new ResponseEntity<>("Usuario no válido.", HttpStatus.BAD_REQUEST);
        }

        boolean plantExists = plantService.plantExists(plant.getName(), plant.getCountry(), plant.getUser().getId());
        if (plantExists) {
            return new ResponseEntity<>("El usuario ya tiene una planta con ese nombre y país", HttpStatus.CONFLICT);
        }

        Optional<User> user = userService.getById(String.valueOf(plant.getUser().getId()));
        if (user.isPresent()) {
            if (plantService.plantExists(plant.getName(), plant.getCountry(),plant.getUser().getId())) {
                return new ResponseEntity<>("La planta ya existe para este usuario.", HttpStatus.CONFLICT);
            }

            plant.setUser(user.get());
            Plant savedPlant = plantService.createPlantWithCategories(plant);
            return new ResponseEntity<>(savedPlant, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
    }



    @CrossOrigin(origins = "*")
    @PutMapping(path ="/update-plant/{id}")
    public ResponseEntity<?> updateBasicPlant(@PathVariable Long id, @RequestBody Plant plant) {
        Optional<Plant> existingPlantOpt = plantService.getById(id);

        if (!existingPlantOpt.isPresent()) {
            return new ResponseEntity<>("Planta no encontrada con ID: " + id, HttpStatus.NOT_FOUND);
        }

        Plant existingPlant = existingPlantOpt.get();
        existingPlant.setName(plant.getName());
        existingPlant.setCountry(plant.getCountry());

        Plant updatedPlant = plantService.save(existingPlant);
        return new ResponseEntity<>(updatedPlant, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/delete-plant/{id}")
    public ResponseEntity<String> deletePlant(@PathVariable Long id) {
        boolean deleted = plantService.deletePlant(id);

        if (deleted) {
            return ResponseEntity.ok("Plant deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant not found");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get-plant/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
        Optional<Plant> plant = plantService.getById(id);
        if (plant.isPresent()) {
            return new ResponseEntity<>(plant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get-all-plants/{userId}")
    public ResponseEntity<List<Plant>> getPlantsByUserId(@PathVariable Long userId) {
        List<Plant> plants = plantService.getPlantsByUserId(userId);
        if (plants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plants, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/get-plant-categories/{plantId}")
    public ResponseEntity<?> getPlantCategories(@PathVariable Long plantId) {
        Optional<Plant> plant = plantService.getById(plantId);

        if (plant.isPresent()) {
            return new ResponseEntity<>(plant.get().getCategoryDataList(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Plant not found", HttpStatus.NOT_FOUND);
    }

}
