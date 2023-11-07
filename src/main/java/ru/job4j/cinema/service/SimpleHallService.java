package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Service
public class SimpleHallService implements HallService {

    private HallRepository hallRepository;

    public SimpleHallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall findById(int id) {
        return hallRepository.findById(id);
    }

    @Override
    public Collection<Integer> getRows(int id) {
        Collection<Integer> rows = new HashSet<>();
        for (int i = 1; i <= findById(id).getRowCount(); i++) {
            rows.add(i);
        }
        return rows;
    }

    @Override
    public Collection<Integer> getPlaces(int id) {
        Collection<Integer> places = new HashSet<>();
        for (int i = 1; i <= findById(id).getPlaceCount(); i++) {
            places.add(i);
        }
        return places;
    }
}
