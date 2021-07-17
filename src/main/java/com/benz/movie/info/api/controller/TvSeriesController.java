package com.benz.movie.info.api.controller;

import com.benz.movie.info.api.dto.request.RequestList;
import com.benz.movie.info.api.entity.TvSeries;
import com.benz.movie.info.api.entity.TvSeriesRate;
import com.benz.movie.info.api.entity.User;
import com.benz.movie.info.api.dto.request.Item;
import com.benz.movie.info.api.dto.request.Rate;
import com.benz.movie.info.api.dto.response.TvSeriesInfo;
import com.benz.movie.info.api.dto.response.UserList;
import com.benz.movie.info.api.service.TvSeriesService;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/info/{id}")
    public ResponseEntity<TvSeriesInfo> findTvSeriesInfo(@PathVariable String id){
        return (id.trim().isEmpty()) ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
               new ResponseEntity<>(tvSeriesService.findTvSeriesInfo(id),HttpStatus.OK);
  
    }

    @PostMapping(value = "/rate",produces = {MediaType.APPLICATION_JSON_VALUE}) //protected resource
    public ResponseEntity<TvSeriesInfo> addRating(@RequestBody Rate rate){
         return (rate.getType_id().trim().isEmpty() || rate.getRate()<=0) ?
                 new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                 new ResponseEntity<>(tvSeriesService.addRating(rate),HttpStatus.OK);
    }

    @PostMapping(value = "/addToFavorite",produces = {MediaType.APPLICATION_JSON_VALUE}) // protected resource
    public ResponseEntity<TvSeriesInfo> addToFavorite(@RequestBody Item item){
          return (item.getEmail().trim().isEmpty() || item.getType_id().trim().isEmpty())
                  ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                  new ResponseEntity<>(tvSeriesService.addToFavoriteList(item),HttpStatus.OK);
    }

    @PostMapping(value = "/addToRemainder",produces = {MediaType.APPLICATION_JSON_VALUE}) //protected resource
    public ResponseEntity<TvSeriesInfo> addToRemainderList(@RequestBody Item item){
        return (item.getEmail().trim().isEmpty() || item.getType_id().trim().isEmpty())
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(tvSeriesService.addToRemainderList(item),HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findUser(@PathVariable String id){
        return (id.trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(tvSeriesService.findUser(id),HttpStatus.OK);

    }

    @PostMapping(value = "/favoriteList",produces = {MediaType.APPLICATION_JSON_VALUE}) //protected resource
    public ResponseEntity<UserList> favoriteTvSeriesByUser(@RequestBody RequestList list){
        return (list.getEmail().trim().isEmpty()) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(tvSeriesService.gatFavoriteTvSeriesByUser(list),HttpStatus.OK);
    }

    @PostMapping(value = "/remainderList",produces = {MediaType.APPLICATION_JSON_VALUE}) //protected resource
    public ResponseEntity<UserList> remainderListByUser(@RequestBody RequestList list){
         return (list.getEmail().trim().isEmpty()) ?
                 new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                 new ResponseEntity<>(tvSeriesService.getRemainderListByUser(list),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TvSeriesInfo>> getTvSeriesInfoBySearch(@RequestParam("name") String name){
        return (name.trim().isEmpty()) ?
                 new ResponseEntity<>(new ArrayList<TvSeriesInfo>(),HttpStatus.OK) :
                new ResponseEntity<>(tvSeriesService.findTvSeriesInfoBySearch(name),HttpStatus.OK);

    }
}
