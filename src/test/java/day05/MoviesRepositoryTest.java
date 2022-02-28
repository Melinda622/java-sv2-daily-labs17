package day05;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MoviesRepositoryTest {

    MoviesRepository moviesRepository;

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
        moviesRepository=new MoviesRepository(dataSource);
        Flyway flyway=Flyway.configure().dataSource(dataSource).load();
        //flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testSaveMovie(){
        Long id=moviesRepository.saveMovie("Titanic", LocalDate.of(1997,12,12));
        System.out.println(id);
    }

    @Test
    public void testUpdate(){
        moviesRepository.updateMovie("Titanic");
    }

}