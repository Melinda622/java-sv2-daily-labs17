package day04;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActorsMoviesServiceTest {

    ActorsRepository actorsRepository;
    MoviesRepository moviesRepository;
    ActorsMoviesRepository actorsMoviesRepository;
    ActorsMoviesService actorsMoviesService;

    @BeforeEach
    public void init () {
        MariaDbDataSource dataSource=new MariaDbDataSource();
        try{
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies_actors_test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("Hungary84");
        }catch(SQLException sqle){
            throw new IllegalStateException("Cannot reach database", sqle);
        }
        actorsRepository=new ActorsRepository(dataSource);
        moviesRepository=new MoviesRepository(dataSource);
        actorsMoviesRepository=new ActorsMoviesRepository(dataSource);
        actorsMoviesService=new ActorsMoviesService(actorsRepository,moviesRepository,actorsMoviesRepository);
    }

    @Test
    public void testInserMovieWithActors() {
        List<String> actors= Arrays.asList("Leonardo Dicaprio","Kate Winslet");
        List<String> actors2= Arrays.asList("Leonardo Dicaprio","John Malkovich","Jeremy Irons","Gerard Depardieu");
        actorsMoviesService.insertMovieWithActors("Titanic", LocalDate.of(1997,12,12),actors);
        actorsMoviesService.insertMovieWithActors("Man in the Iron Mask", LocalDate.of(1998,12,12),actors2);
    }
}