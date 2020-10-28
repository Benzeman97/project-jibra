package com.benz.user.api.service.impl;

import com.benz.user.api.dao.UserDAO;
import com.benz.user.api.entity.User;
import com.benz.user.api.service.UserService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@DisplayName("UserServiceTest")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDAO userDAO;

    /*@Test
    @DisplayName("getUser")
    public void getUserTest()
    {
        User expectedUser=getUser();

        Mockito.when(userDAO.findUser(expectedUser.getUserId(),expectedUser.getNicOrPassport())).thenReturn(Optional.of(expectedUser));

        User actualUser=userService.getUser(expectedUser);

        assertEquals(expectedUser,actualUser,"saved user should be returned");

    }
*/
    @Test
    @DisplayName("getUsers")
    public void getUsersTest()
    {
        System.out.println("get Users");
    }
/*
    private User getUser()
    {
         return new User(1001L,"Nafaz","Benzema","nafazbenzema@gmail.com","Singapore",
                 LocalDate.of(1997,05,02),"+94 71 0645 6988","{bcrypt}gdnksne$#bsjdndjenke%hs&","971230720V",new Date());
    }

    private List<User> getUsers()
    {
        User user1=new User(1001L,"Nafaz","Benzema","nafazbenzema@gmail.com","Singapore",
                LocalDate.of(1997,05,02),"+94 71 0645 6988","{bcrypt}gdnksne$#bsjdndjenke%hs&","971230720V",new Date());

        User user2=new User(1002L,"Kelly","Brook","brook@hotmail.com","UK",
                LocalDate.of(1979,10,22),"+32 87 765 5382","{bcrypt}gdnksnddm%#jdndjenke%hs&","7953728403GB",new Date());

        return new ArrayList<>(Arrays.asList(user1,user2));
    }*/
}
