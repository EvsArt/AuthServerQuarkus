package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entity.Player;
import org.acme.repository.PlayerRepository;

import java.sql.SQLException;

@ApplicationScoped
public class PlayerService {

    @Inject
    PlayerRepository playerRepository;

    public Player getPlayerByLogin(String login) {
        try {
            return playerRepository.getByLogin(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
