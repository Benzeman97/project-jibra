package com.benz.movie.info.api.controller;

import com.benz.movie.info.api.model.Rating;
import com.benz.movie.info.api.security.AuthEntryPoint;
import com.benz.movie.info.api.service.MovieInfoService;
import com.benz.movie.info.api.service.MovieUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@ExtendWith({SpringExtension.class})
@DisplayName("MovieUserControllerTest")
public class MovieUserControllerTest {

    @MockBean
    private MovieUserService movieUserService;

    @MockBean
    private MovieInfoService movieInfoService;

    @MockBean
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("getMovieRateTest")
    public void getMovieRateTest() throws Exception {

        String movieName = "Harry Potter 1";
        int rate = 6;

        Mockito.when(movieUserService.ratingBasedOnUser(Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(getRating());

        MvcResult result = mockMvc.perform(post("/movie/rated?name={name}&rate={rate}", movieName, rate).accept(new String[]{MediaType.APPLICATION_JSON_VALUE})
                .header("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW92aWUtaW5mby1zZXJ2aWNlIiwicGF5bWVudC1zZXJ2aWNlIiwidXNlci1pbmZvLXNlcnZpY2UiLCJ3ZWItcG9ydGFsIl0sInVzZXJfbmFtZSI6Im5hZmF6YmVuemVtYUBnbWFpbC5jb20iLCJzY29wZSI6WyJjcmVhdGUiLCJyZWFkIiwidXBkYXRlIiwiZGVsZXRlIl0sImV4cCI6MTYxOTU1MTUxNCwiYXV0aG9yaXRpZXMiOlsiQ0FOX0NSRUFURSIsIlJPTEVfVVNFUiIsIkNBTl9ERUxFVEUiLCJST0xFX0FETUlOIiwiUk9MRV9NT0RFUkFUT1IiLCJDQU5fUkVBRCIsIkNBTl9VUERBVEUiXSwianRpIjoiMmU2YWEzZGMtOWZiNy00ZTM5LTg2NDUtYTQwNDFhZTBiY2MzIiwiY2xpZW50X2lkIjoid2ViIn0.lLGt_EfggGPS6c-VQ52-UIGhrueVcfn_hpiH9c_6TDUujvf-58PFCxx_eqsLB6ke23UNfqwmMPmHYf3Gytk2n8BlbOVxXVdRv-yAvl2FvS6zhgK4ljXhBhgD7fBZhw2jHh3INR2EfzgunLtlU-76m5ivncN54cYiTl8dXveExOkhI5W-9lsriXROlcrdG8HWAS5XDU7aCdRVv9igc7ijdhBUtUOqik4sQjHsnGOQWafV-iXx-5x_Bk62rVhjWxdn8OLVZaWiK5S9KObb7X3U0UKvdH9gWPiWNBVIawDy16e7hDTJT0DplXqNr-yjKt1trTNBipDPy7nT6CvyyK721A"))
                .andExpect(status().isOk()).andReturn();

        int actualStatus = result.getResponse().getStatus();

        Assertions.assertEquals(HttpStatus.OK.value(), actualStatus, () -> String.format("expected %d but response was %d", HttpStatus.OK.value(), actualStatus));

    }

    private Rating getRating() {
        Rating rating = new Rating();
        rating.setRate(6.0);
        rating.setStar(3);
        rating.setTotalPoints(350);
        return rating;
    }
}
