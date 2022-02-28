package day01;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorsRepository {

    private DataSource datasource;

    public ActorsRepository(DataSource datasource) {
        this.datasource = datasource;
    }

    public void saveActor(String name) {

        try (Connection connection = datasource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("insert into actors (actor_name) values(?)")) {
            stmt.setString(1, name);
            stmt.execute();
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by inserting", sqle);
        }

    }

    public List<String> findActorsWithPrefix(String prefix) {
        try (Connection connection = datasource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("select*from actors where actor_name like ?")) {
            stmt.setString(1, prefix + "%");
            return prepareResult(stmt, "actor_name");
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by query", sqle);
        }
    }

    private List<String> prepareResult(PreparedStatement stmt, String columnLabel) {
        List<String> result = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString(columnLabel);
                result.add(name);
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by query", sqle);
        }
        return result;
    }
}
