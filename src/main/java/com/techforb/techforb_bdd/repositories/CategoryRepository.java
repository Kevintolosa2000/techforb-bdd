package com.techforb.techforb_bdd.repositories;

import com.techforb.techforb_bdd.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c ORDER BY c.idCategory ASC")
    List<Category> findAllOrderedById();

}
