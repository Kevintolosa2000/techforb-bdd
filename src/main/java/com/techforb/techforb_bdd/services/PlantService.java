package com.techforb.techforb_bdd.services;

import com.techforb.techforb_bdd.models.Category;
import com.techforb.techforb_bdd.models.CategoryData;
import com.techforb.techforb_bdd.models.Plant;
import com.techforb.techforb_bdd.repositories.CategoryRepository;
import com.techforb.techforb_bdd.repositories.PlantRepository;
import com.techforb.techforb_bdd.repositories.CategoryDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDataRepository categoryDataRepository;

    public Plant createPlantWithCategories(Plant plant) {

        Plant savedPlant = plantRepository.save(plant);

        List<Category> categories = categoryRepository.findAllOrderedById();

        for (Category category : categories) {
            CategoryData categoryData = new CategoryData();
            categoryData.setPlant(savedPlant);
            categoryData.setCategory(category);


            categoryDataRepository.save(categoryData);
        }

        return savedPlant;
    }

    public Plant save(Plant plant) {

        if (plant.getId_plant() == null || !plantRepository.existsById(plant.getId_plant())) {
            throw new RuntimeException("La planta no existe o no tiene ID válido.");
        }
        return plantRepository.save(plant);
    }

    public Optional<Plant> getById(Long id) {
        return plantRepository.findById(id);
    }

    public Boolean deletePlant(Long id) {
        try {
            plantRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Plant> getPlantsByUserId(Long userId) {
        return plantRepository.findPlantsByUserId(userId);
    }

    public boolean plantExists(String name, String country, Long userId) {
        return plantRepository.findByNameAndCountry(name, country, userId).isPresent();
    }


}
