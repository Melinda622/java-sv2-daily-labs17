package day02;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

        Flyway flyway=Flyway.configure().dataSource(dataSource).load();
        //.clean();
        flyway.migrate();


        /*try(Connection connetion=dataSource.getConnection()){
            Statement stmt=connetion.createStatement();
            stmt.executeUpdate("insert into actors (actor_name) values('John Doe')");
        }catch(SQLException sqle){
            throw new IllegalStateException("Cannot read",sqle);
        }*/

        /*ActorsRepository actorsRepository=new ActorsRepository(dataSource);
        actorsRepository.saveActor("Jack Doe");
        System.out.println(actorsRepository.findActorsWithPrefix("Ja"));*/

    }
}
