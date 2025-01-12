package com.techforb.techforb_bdd.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name = "category_data")
public class CategoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plant", nullable = false)
    @JsonBackReference
    private Plant plant;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    private int readings;

    private int mediumAlerts;

    private int redAlerts;

    private int disabledSensors;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getReadings() {
        return readings;
    }

    public void setReadings(int readings) {
        this.readings = readings;
    }

    public int getMediumAlerts() {
        return mediumAlerts;
    }

    public void setMediumAlerts(int mediumAlerts) {
        this.mediumAlerts = mediumAlerts;
    }

    public int getRedAlerts() {
        return redAlerts;
    }

    public void setRedAlerts(int redAlerts) {
        this.redAlerts = redAlerts;
    }

    public int getDisabledSensors() {
        return disabledSensors;
    }

    public void setDisabledSensors(int disabledSensors) {
        this.disabledSensors = disabledSensors;
    }
}
