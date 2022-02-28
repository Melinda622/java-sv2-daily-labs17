package day02;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActorsRepositoryTest {

    ActorsRepository actorsRepository;

    @BeforeEach
    public void init(){
        MariaDbDataSource dataSource=new MariaDbDataSource();
        try{
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies_actors_test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("Hungary84");
        }catch(SQLException sqle){
            throw new IllegalStateException("Cannot reach database", sqle);
        }

        Flyway flyway=Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        actorsRepository=new ActorsRepository(dataSource);
        actorsRepository.saveActor("John Doe");
        actorsRepository.saveMovie("Titanic", LocalDate.of(1997,12,12));
    }

    @Test
    public void testSaveActor(){
        actorsRepository.saveActor("Jack Doe");
    }

    @Test
    public void testSaveMovie(){
        actorsRepository.saveMovie("Jurassic Park", LocalDate.of(1993,12,12));
        List<Movie>result=Arrays.asList(new Movie(2L,"Jurassic Park",LocalDate.of(1993,12,12)));
        assertEquals(result.get(0).getTitle(),actorsRepository.listMovies().get(1).getTitle());
        assertEquals(result.get(0).getLocalDate(),actorsRepository.listMovies().get(1).getLocalDate());
    }

    @Test
    public void testListMovies(){
        //actorsRepository.saveMovie("Titanic", LocalDate.of(1997,12,12));
        List<Movie> result= Arrays.asList(new Movie(1L,"Titanic",LocalDate.of(1997,12,12)));
        //assertEquals(result, actorsRepository.listMovies());
        assertEquals(result.get(0).getTitle(),actorsRepository.listMovies().get(0).getTitle());
        assertEquals(result.get(0).getLocalDate(),actorsRepository.listMovies().get(0).getLocalDate());
    }
}