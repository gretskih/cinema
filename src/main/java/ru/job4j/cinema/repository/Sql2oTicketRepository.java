package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

public class Sql2oTicketRepository implements TicketRepository {
    private final Sql2o sql2o;
    private static final Logger LOG = LoggerFactory.getLogger(Sql2oTicketRepository.class);

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO tickets (id, session_id, row_number, place_number, user_id) VALUES (:id, :sessionId, :rowNumber, :placeNumber, :userId)", true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Exception e) {
            LOG.error(e.getCause().toString());
        }
        return Optional.empty();
    }
}
