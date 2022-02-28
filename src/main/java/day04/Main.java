package day04;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        MariaDbDataSource dataSource=new MariaDbDataSource();
        try{
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies_actors_test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("Hungary84");
        }catch(SQLException sqle){
            throw new IllegalStateException("Cannot reach database", sqle);
        }


        ActorsRepository actorsRepository=new ActorsRepository(dataSource);
        //actorsRepository.saveActor("Jack Doe");
        //System.out.println(actorsRepository.findActorsWithPrefix("Ja"));*/
        //System.out.println(actorsRepository.saveActor("John Doe"));
        System.out.println(actorsRepository.findActorsByName("John Doe"));

    }
}
