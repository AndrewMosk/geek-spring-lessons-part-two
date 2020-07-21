package ru.geekbrains.service.model;

import ru.geekbrains.model.Brand;

import java.util.List;
import java.util.Map;

public class SelectedFilters {

    private int id;
    private List<Brand> brands;
    private String category;

    public SelectedFilters(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
