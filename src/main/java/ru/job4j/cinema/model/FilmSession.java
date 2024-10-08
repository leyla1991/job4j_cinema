package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class FilmSession {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "halls_id", "hallsId",
            "start_time", "startId",
            "end_time", "endTime",
            "price", "price"
    );

    private int id;

    private int filmId;

    private int hallsId;

    private LocalDateTime startId;

    private LocalDateTime endTime;

    private int price;

    public FilmSession(int id, int filmId, int hallsId, LocalDateTime startId, LocalDateTime endTime, int price) {
        this.id = id;
        this.filmId = filmId;
        this.hallsId = hallsId;
        this.startId = startId;
        this.endTime = endTime;
        this.price = price;
    }

    public FilmSession() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getHallsId() {
        return hallsId;
    }

    public void setHallsId(int hallsId) {
        this.hallsId = hallsId;
    }

    public LocalDateTime getStartId() {
        return startId;
    }

    public void setStartId(LocalDateTime startId) {
        this.startId = startId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSession that = (FilmSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
