package com.benz.movie.info.api.controller;

import com.benz.movie.info.api.entity.TvSeries;
import com.benz.movie.info.api.entity.TvSeriesRate;
import com.benz.movie.info.api.model.TvSeriesInfo;
import com.benz.movie.info.api.service.TvSeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/tv-series")
public class TvSeriesController {

    private TvSeriesService tvSeriesService;

    public TvSeriesController(TvSeriesService tvSeriesService){
        this.tvSeriesService=tvSeriesService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TvSeries>> getTvSeries(){
         return ResponseEntity.ok(tvSeriesService.getTvSeries());
    }

    @GetMapping(value="/rate",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TvSeriesRate> findTvSeriesRate(@RequestParam("name") String name){
        return (name.trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
        new ResponseEntity<>(tvSeriesService.findTvSeriesRate(name),HttpStatus.OK);
    }

    @GetMapping(value = "/info",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TvSeriesInfo>> getTvSeriesInfo(){
        return ResponseEntity.ok(tvSeriesService.getTvSeriesInfo());
    }
}
