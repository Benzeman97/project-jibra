package com.benz.movie.info.api.controller;

import com.benz.movie.info.api.dto.request.Rating;
import com.benz.movie.info.api.service.MovieUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/movie")
public class MovieUserController {

    private MovieUserService movieUserService;

    public MovieUserController(MovieUserService movieUserService) {
        this.movieUserService = movieUserService;
    }

    @PostMapping(value = "/rated", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODERATOR','ROLE_ADMIN')")
    public ResponseEntity<Rating> getMovieRate(@RequestParam("name") String movieName, @RequestParam("rate") int rate) {

        if (movieName.trim().isEmpty() && rate > 10 && rate < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(movieUserService.ratingBasedOnUser(movieName, rate));

    }

}
