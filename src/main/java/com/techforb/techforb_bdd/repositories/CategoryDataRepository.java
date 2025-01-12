package com.techforb.techforb_bdd.repositories;
import com.techforb.techforb_bdd.models.CategoryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDataRepository extends JpaRepository<CategoryData, Long> {
    @Query("SELECT cd FROM CategoryData cd WHERE cd.plant.id_plant = :plantId")
    List<CategoryData> findByPlantId(@Param("plantId") Long plantId);
}

