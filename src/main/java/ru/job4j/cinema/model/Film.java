package ru.job4j.cinema.model;

import java.util.Objects;

public class Film {
    private int id;
    private String name;
    private String description;
    private Detail detail;
    private int fileId;

    public Film() {
    }

    public Film(int id, String name, String description, Detail detail, int fileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileId = fileId;
        this.detail = detail;
    }

    public static class Detail {
        private int year;
        private int genreId;
        private int minimalAge;
        private int durationInMinutes;

        public Detail(int year, int genreId, int minimalAge, int durationInMinutes) {
            this.year = year;
            this.genreId = genreId;
            this.minimalAge = minimalAge;
            this.durationInMinutes = durationInMinutes;
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return detail.year;
    }

    public void setYear(int year) {
        detail.year = year;
    }

    public int getGenreId() {
        return detail.genreId;
    }

    public void setGenreId(int genreId) {
        detail.genreId = genreId;
    }

    public int getMinimalAge() {
        return detail.minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        detail.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return detail.durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        detail.durationInMinutes = durationInMinutes;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
