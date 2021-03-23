package com.benz.movie.info.api.controller;

import com.benz.movie.info.api.entity.Movie;
import com.benz.movie.info.api.service.MovieInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieInfoController {

    private MovieInfoService movieInfoService;

    public MovieInfoController(MovieInfoService movieInfoService)
    {
        this.movieInfoService=movieInfoService;
    }

    @GetMapping(value = "/{movieId}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Movie> findMovieInfo(@PathVariable String movieId)
    {
        return !movieId.trim().isEmpty() ? new ResponseEntity<>(movieInfoService.findMovieInfo(movieId),HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
