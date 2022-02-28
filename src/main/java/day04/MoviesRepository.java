package day04;

import day02.Movie;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MoviesRepository {

    DataSource dataSource;

    public MoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long saveMovie(String title, LocalDate releaseDate) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("insert into movies (actor_name,release_date) values(?,?)",
        Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1,title);
            stmt.setDate(2, Date.valueOf(releaseDate));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
               if(rs.next()){
                  return rs.getLong("id");
               }
               throw new IllegalStateException("Cannot insert new movie");
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Error by inserting", sqle);
        }
    }

    public List<Movie> listMovies(){
        List<Movie>result=new ArrayList<>();

        try (
                Connection connection=dataSource.getConnection();
                Statement stmt=connection.createStatement();
                ResultSet rs = stmt.executeQuery("select*from movies")) {
            while(rs.next()){
                long id=rs.getInt("id");
                String title=rs.getString("actor_name");
                LocalDate date=rs.getDate("release_date").toLocalDate();
                result.add(new Movie(id,title,date));
            }
        }catch (Exception sqle) {
            throw new IllegalStateException("Cannot insert");
        }
        return result;
    }
}
