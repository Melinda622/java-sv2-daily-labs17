package day05;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RatingsRepository {

    DataSource dataSource;

    public RatingsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   /* public void insertRatings(long movieId, List<Long> ratings) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("insert into ratings (movie_id, rating) values(?,?)")) {
            stmt.setLong(1, movieId);
            stmt.setLong(2, rating);
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot insert to ratings", sqle);
        }
    }*/

    public void insertRatings(long movieId, List<Long> ratings) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement("insert into ratings (movie_id, rating) values(?,?)")) {

                for (Long rating : ratings) {
                    if (rating < 1 || rating > 5) {
                        throw new IllegalArgumentException("invalid rating");
                    }
                    stmt.setLong(1, movieId);
                    stmt.setLong(2, rating);
                    stmt.executeUpdate();

                }
                connection.commit();

            } catch (IllegalArgumentException iae) {
                connection.rollback();
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot insert to ratings", sqle);
        }
    }
}
