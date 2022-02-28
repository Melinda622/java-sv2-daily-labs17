package day05;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoviesRepository {

    DataSource dataSource;

    public MoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long saveMovie(String title, LocalDate releaseDate) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("insert into movies (actor_name,release_date) values(?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, title);
            stmt.setDate(2, Date.valueOf(releaseDate));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
                throw new IllegalStateException("Cannot insert new movie");
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by inserting", sqle);
        }
    }

    public List<Movie> listMovies() {
        List<Movie> result = new ArrayList<>();

        try (
                Connection connection = dataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select*from movies")) {
            while (rs.next()) {
                long id = rs.getInt("id");
                String title = rs.getString("actor_name");
                LocalDate date = rs.getDate("release_date").toLocalDate();
                result.add(new Movie(id, title, date));
            }
        } catch (Exception sqle) {
            throw new IllegalStateException("Cannot insert");
        }
        return result;
    }

    public Optional<Movie> findMovieByTitle(String title) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("select*from movies where actor_name=?")) {
            stmt.setString(1, title);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getResultSet()) {
                if (rs.next()) {
                    long id = rs.getLong("id");
                    String filmTitle = rs.getString("actor_name");
                    LocalDate date = rs.getDate("release_date").toLocalDate();
                    return Optional.of(new Movie(id, filmTitle, date));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot connect to movies");
        }
    }

    public double getMovieAvgRating(String title) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement
                     ("select avg(rating) as avg from ratings join movies on ratings.movie_id=movies.id where movies.actor_name=?")) {
            stmt.setString(1, title);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg");
                }
                throw new IllegalStateException("Cannot find movie");
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Error by query", sqle);
        }
    }

    public void updateMovie(String title){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement
                     ("update movies set avg_rating=? where actor_name=?")) {
            stmt.setDouble(1,getMovieAvgRating(title));
            stmt.setString(2, title);
            stmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot query", sqle);
        }
    }
}
