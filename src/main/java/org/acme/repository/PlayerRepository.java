package org.acme.repository;

import io.quarkus.arc.InjectableInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import org.acme.entity.Player;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class PlayerRepository {
    @Inject
    @Any
    InjectableInstance<DataSource> dataSourceInj;

    public Player getByLogin(String login) throws SQLException {
        DataSource dataSource = dataSourceInj.getActive();
        try (Connection connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("select * from players where login = ?");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? toPlayerEntity(resultSet) : null;
        }

    }

    private Player toPlayerEntity(ResultSet rs) throws SQLException {
        return new Player(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3)
        );
    }

}
