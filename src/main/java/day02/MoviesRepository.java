package day02;

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

    public void saveMovie(String title, LocalDate releaseDate) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("insert into movies (actor_name,release_date) values(?,?)")) {
            stmt.setString(1,title);
            stmt.setDate(2, Date.valueOf(releaseDate));
            stmt.executeUpdate();
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
