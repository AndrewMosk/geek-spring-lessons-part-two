package ru.geekbrains.controller.repr;

import ru.geekbrains.model.Category;

import java.io.Serializable;

public class CategoryRepr implements Serializable {

    private long id;

    private String name;

    private long parent_id;

    public CategoryRepr(long id, String name, long parent_id) {
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
    }

    public CategoryRepr(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parent_id = category.getParent_id();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }
}
