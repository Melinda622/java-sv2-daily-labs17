package day05;

import day04.MoviesRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RatingsRepositoryTest {

    RatingsRepository ratingsRepository;

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
       ratingsRepository=new RatingsRepository(dataSource);
        Flyway flyway=Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void insertRatings(){
        List<Long> ratings= Arrays.asList(5L,5L,3L,4L);
        ratingsRepository.insertRatings(1,ratings);
    }

}