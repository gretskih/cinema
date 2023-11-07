package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Hall;

import java.util.Collection;

public interface HallService {
    Hall findById(int id);

    Collection<Integer> getRows(int id);

    Collection<Integer> getPlaces(int id);
}
