package com.techforb.techforb_bdd.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plant")
    private Long id_plant;

    @NotBlank(message = "El nombre de la planta no puede estar vacio")
    private String name;

    @NotBlank(message = "El pais no puede estar vacio")
    private String country;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CategoryData> categoryDataList;

    public List<CategoryData> getCategoryDataList() {
        return categoryDataList;
    }

    public void setCategoryDataList(List<CategoryData> categoryDataList) {
        this.categoryDataList = categoryDataList;
    }

    public Long getId_plant() {
        return id_plant;
    }

    public void setId_plant(Long id_plant) {
        this.id_plant = id_plant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
