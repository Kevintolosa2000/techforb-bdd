package com.techforb.techforb_bdd.services;
import com.techforb.techforb_bdd.models.CategoryData;
import com.techforb.techforb_bdd.repositories.CategoryDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CategoryDataService {
    @Autowired
    private CategoryDataRepository categoryDataRepository;

    public Optional<CategoryData> getById(Long id) {
        return categoryDataRepository.findById(id);
    }

    public CategoryData save(CategoryData categoryData) {
        return categoryDataRepository.save(categoryData);
    }

}
