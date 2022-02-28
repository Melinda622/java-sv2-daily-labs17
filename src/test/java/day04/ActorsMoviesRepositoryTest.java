package day04;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ActorsMoviesRepositoryTest {

    ActorsMoviesRepository actorsMoviesRepository;

    @BeforeEach
    public void init() {
        MariaDbDataSource dataSource=new MariaDbDataSource();
        try{
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies_actors_test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("Hungary84");
        }catch(SQLException sqle){
            throw new IllegalStateException("Cannot reach database", sqle);
        }
        actorsMoviesRepository=new ActorsMoviesRepository(dataSource);

        Flyway flyway=Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testInsert() {
        //actorsMoviesRepository.insertActorAndMovieId(1,1);
    }
}