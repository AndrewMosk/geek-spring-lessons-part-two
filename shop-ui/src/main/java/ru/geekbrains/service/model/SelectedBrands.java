package ru.geekbrains.service.model;

import ru.geekbrains.model.Brand;

import java.util.List;
import java.util.Map;

public class SelectedBrands {

    private int id;
    private List<Brand> brands;

    public SelectedBrands(int id) {
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
}
