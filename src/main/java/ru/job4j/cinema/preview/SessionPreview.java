package ru.job4j.cinema.preview;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionPreview {
    private int id;
    private String fileName;
    private String hallName;
    private LocalDateTime startTime;
    private int price;

    public SessionPreview(int id, String fileName, String hallName, LocalDateTime startTime, int price) {
        this.id = id;
        this.fileName = fileName;
        this.hallName = hallName;
        this.startTime = startTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionPreview that = (SessionPreview) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
