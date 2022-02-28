package day05;

import java.util.Arrays;
import java.util.Optional;

public class MovingsRatingsService {

    MoviesRepository moviesRepository;
    RatingsRepository ratingsRepository;

    public MovingsRatingsService(MoviesRepository moviesRepository, RatingsRepository ratingsRepository) {
        this.moviesRepository = moviesRepository;
        this.ratingsRepository = ratingsRepository;
    }

    public void addRatings(String title, Long... ratings){
        Optional<Movie> actual=moviesRepository.findMovieByTitle(title);
        if(actual.isPresent()){
            ratingsRepository.insertRatings(actual.get().getId(), Arrays.asList(ratings));
        }else{
            throw new IllegalArgumentException("Cannot find movie"+title);
        }
    }
}
