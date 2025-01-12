package com.techforb.techforb_bdd.repositories;

import com.techforb.techforb_bdd.models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByUserId(Long userId);

    @Query("SELECT p FROM Plant p WHERE p.user.id = :userId")
    List<Plant> findPlantsByUserId(Long userId);

    @Query("SELECT p FROM Plant p WHERE LOWER(p.name) = LOWER(:name) AND LOWER(p.country) = LOWER(:country) AND p.user.id = :userId" )
    Optional<Plant> findByNameAndCountry(String name, String country, Long userId);

}
