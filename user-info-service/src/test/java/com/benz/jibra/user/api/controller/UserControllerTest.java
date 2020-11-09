package com.benz.jibra.user.api.controller;

import com.benz.jibra.user.api.entity.User;
import com.benz.jibra.user.api.security.AuthEntryPoint;
import com.benz.jibra.user.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebMvcTest
@DisplayName("UserControllerTest")
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthEntryPoint authEntryPoint;

   final private static Logger LOG= LogManager.getLogger(UserControllerTest.class);


    @Test
    @DisplayName("getUserTest")
    public void getUserTest() throws Exception
    {

          String expectedUser=new ObjectMapper().writeValueAsString(getUser_1());

        Mockito.when(userService.getUser(Mockito.any(User.class))).thenReturn(getUser_1());

          MvcResult result=mockMvc.perform(get("/api/user/one").contentType(MediaType.APPLICATION_JSON_VALUE).content(expectedUser).accept(new String[]{MediaType.APPLICATION_JSON_VALUE}))
                  .andExpect(status().isOk())
                 // .andExpect(content().string(expectedUser))
                  .andReturn();

        LOG.info(result.getResponse().getContentAsString());

        int actualStatus= result.getResponse().getStatus();

          assertEquals(HttpStatus.OK.value(),actualStatus,()->"expected "+HttpStatus.OK.value()+" but returned "+actualStatus);

    }

    @Test
    @DisplayName("saveUserTest")
    @Disabled(value = "password is needed to perform this task")
    public void saveUserTest() throws Exception
    {
        String expectedUser=new ObjectMapper().writeValueAsString(getUser_1());


        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(getUser_1());


        MvcResult result=mockMvc.perform(post("/api/user/save").contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(expectedUser).accept(new String[]{MediaType.APPLICATION_JSON_VALUE}))
                .andExpect(status().isOk())
                .andReturn();

        LOG.info(result.getResponse().getContentAsString());

        int actualStatus=result.getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(),actualStatus,()->"expected "+HttpStatus.OK.value()+" but was "+actualStatus);


    }



    private User getUser_1() throws ParseException {

        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");

        User user=new User();
        user.setUserId(1001);
        user.setNicOrPassport("971230720V");
        user.setFirstName("Nafaz");user.setLastName("Benzema");
        user.setEmail("nafazbenzema@gmail.com");
        user.setTeleNo("+97 7 164 5162");
        user.setCountry("Sri Lanka");
        user.setDob(dateFormat.parse("02-05-1997"));
        user.setRegisteredDate(new Date());
        user.setModifiedDate(new Date());

        return user;
    }

    private User getUser_2() throws ParseException {

        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");

        User user=new User();
        user.setUserId(1002);
        user.setNicOrPassport("4544678633456UK");
        user.setFirstName("Kelly");user.setLastName("Brook");
        user.setEmail("brook@hotmail.com");
        user.setTeleNo("+32 87 765 5382");
        user.setCountry("United Kingdom");
        user.setDob(dateFormat.parse("22-10-1979"));
        user.setRegisteredDate(new Date());
        user.setModifiedDate(new Date());

        return user;
    }


    private List<User> getUsers() throws Exception
    {
        return new ArrayList<>(Arrays.asList(getUser_1(),getUser_2()));
    }
}
