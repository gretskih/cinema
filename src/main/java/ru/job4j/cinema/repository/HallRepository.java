package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Hall;

import java.util.Optional;

public interface HallRepository {
    Hall findById(int id);
}
