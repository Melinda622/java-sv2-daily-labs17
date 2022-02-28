package day04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ActorsMoviesService {

    ActorsRepository actorsRepository;
    MoviesRepository moviesRepository;
    ActorsMoviesRepository actorsMoviesRepository;

    public ActorsMoviesService(ActorsRepository actorsRepository, MoviesRepository moviesRepository, ActorsMoviesRepository actorsMoviesRepository) {
        this.actorsRepository = actorsRepository;
        this.moviesRepository = moviesRepository;
        this.actorsMoviesRepository = actorsMoviesRepository;
    }

    public void insertMovieWithActors(String title, LocalDate releaseDate, List<String> actorNames) {
        Long movieId = moviesRepository.saveMovie(title, releaseDate);
        Long actorId;

        for (String s : actorNames) {
            Optional<Actor> found = actorsRepository.findActorsByName(s);
            if (found.isPresent()) {
                actorId = found.get().getId();
            } else {
                actorId = actorsRepository.saveActor(s);
            }
            actorsMoviesRepository.insertActorAndMovieId(actorId, movieId);
        }
    }

}
