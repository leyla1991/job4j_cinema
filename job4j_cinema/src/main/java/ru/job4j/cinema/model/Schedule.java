package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Schedule {

    private int id;

    private String name;

    private String description;

    private LocalDateTime create = LocalDateTime.now();

    public Schedule(int id, String name, String description, LocalDateTime create) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.create = create;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    public void setCreate(LocalDateTime create) {
        this.create = create;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule that = (Schedule) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
