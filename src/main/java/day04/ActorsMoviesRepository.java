package day04;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsMoviesRepository {

    DataSource dataSource;

    public ActorsMoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   public void insertActorAndMovieId(long actor_id, long movie_id){

       try  (Connection connection=dataSource.getConnection();
       PreparedStatement stmt=connection.prepareStatement("insert into movies_actors (actor_id, movie_id) values(?,?)")) {
           stmt.setLong(1,actor_id);
           stmt.setLong(2,movie_id);
           stmt.executeUpdate();
       } catch (SQLException sqle) {
           throw new IllegalStateException("Cannot insert row to to movies_actors");
       }
   }
}
