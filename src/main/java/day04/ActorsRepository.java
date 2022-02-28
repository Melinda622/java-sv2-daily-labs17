package day04;

import javax.sql.DataSource;
import java.sql.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActorsRepository {

    private DataSource dataSource;

    public ActorsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long saveActor(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("insert into actors (actor_name) values(?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
                throw new IllegalStateException("Error by inserting");
            }
        } catch (
                SQLException sqle) {
            throw new IllegalArgumentException("Error by inserting", sqle);
        }
    }

    /*public Optional<String> findActorsByName(String name) {
        List<String> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("select*from actors_name where actor_name=?")) {
            stmt.setString(1, name);
            result=prepareResult(stmt, "actor_name");
            return result.stream().findFirst();
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by query", sqle);
        }
    }*/

    public Optional<Actor> findActorsByName(String name) {
        List<String> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("select*from actors where actor_name=?")) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            return returnActor(stmt);
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by query", sqle);
        }
    }


    private Optional<Actor> returnActor(PreparedStatement stmt) {
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("actor_name");
                return Optional.of(new Actor(id, name));
            } else {
                return Optional.empty();
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error", sqle);
        }
    }
}
