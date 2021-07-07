package com.benz.movie.info.api.controller;

import com.benz.movie.info.api.entity.Movie;
import com.benz.movie.info.api.service.MovieInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieInfoController {

    private MovieInfoService movieInfoService;

    final private static Logger LOGGER = LogManager.getLogger(MovieInfoController.class);

    public MovieInfoController(MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @GetMapping(value = "/{movieId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<Movie> findMovieInfo(@PathVariable String movieId) {
        return !movieId.trim().isEmpty() ? new ResponseEntity<>(movieInfoService.findMovieInfo(movieId), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<List<Movie>> getMovieInfoList() {
        LOGGER.info("return movie info list");
        return ResponseEntity.ok(movieInfoService.getMovieInfo());
    }

    @PostMapping(value = "/save", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<Movie> saveMovieInfo(@RequestBody Movie movie) {
        return (movie.getMovieId().trim().isEmpty() && movie.getMovieName().trim().isEmpty() && movie.getPrice() == 0.0 && movie.getSchedule().size() == 0 && movie.getCategory().trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(movieInfoService.saveMovieInfo(movie), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{movieId}/update", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<Movie> updateMovieInfo(@PathVariable String movieId, @RequestBody Movie movie) {
        return (movie.getMovieId().trim().isEmpty() && movie.getMovieName().trim().isEmpty() && movie.getPrice() == 0.0 && movie.getSchedule().size() == 0 && movie.getCategory().trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(movieInfoService.updateMovieInfo(movieId, movie), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{movieId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteMovieInfo(@PathVariable String movieId) {
        if (movieId.trim().isEmpty())
            throw new IllegalArgumentException("movie id is required");
        movieInfoService.deleteMovieInfo(movieId);
    }
}
