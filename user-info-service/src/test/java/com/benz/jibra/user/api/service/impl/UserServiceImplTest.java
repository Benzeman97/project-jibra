package com.benz.jibra.user.api.service.impl;

import com.benz.jibra.user.api.dao.UserDAO;
import com.benz.jibra.user.api.entity.User;
import com.benz.jibra.user.api.entity.UserIdentity;
import com.benz.jibra.user.api.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@DisplayName("UserServiceTest")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDAO userDAO;

    private Logger logger = LogManager.getLogger(UserServiceImplTest.class);

    @Test
    @DisplayName("getUserTest")
    public void getUserTest() throws Exception {

        User expectedUser = getUser_1();

        Mockito.when(userDAO.findById(new UserIdentity(expectedUser.getUserId(), expectedUser.getNicOrPassport()))).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getUser(expectedUser);

        assertEquals(expectedUser, actualUser, expectedUser + " should be returned");
    }

    @Test
    @DisplayName("getUsersTest")
    public void getUsersTest() throws Exception {
        List<User> expectedUsers = getUsers();

        Mockito.when(userDAO.findAllUsers()).thenReturn(Optional.of(expectedUsers));

        List<User> actualUsers = userService.getUsers();

        assertEquals(expectedUsers, actualUsers, "the list of the user should be returned");
    }

    @Test
    @DisplayName("saveUserTest")
    public void saveUserTest() throws Exception {

        User expectedUser = getUser_1();

        Mockito.when(userDAO.existsUserByNicOrPassport(expectedUser.getNicOrPassport())).thenReturn(getUser_1());

        Mockito.when(userDAO.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = userDAO.save(expectedUser);

        assertEquals(expectedUser, actualUser, "save and expected user should be returned");
    }

    @Test
    @DisplayName("updateUserTest")
    public void updateUserTest() throws Exception {
        User expectedUser = getUser_1();

        expectedUser.setFirstName("benz");
        expectedUser.setTeleNo("+94 71 93455434");

        Mockito.when(userDAO.existsUserByUserIdAndNicOrPassport(expectedUser.getUserId(), expectedUser.getNicOrPassport())).thenReturn(expectedUser);

        Mockito.when(userDAO.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = userDAO.save(expectedUser);

        assertEquals(expectedUser, actualUser, "update and expected user should be returned");
    }

    @Test
    @DisplayName("deleteUserTest")
    public void deleteUserTest() throws Exception {
        User user = getUser_1();

        Mockito.when(userDAO.existsUserByUserIdAndNicOrPassport(user.getUserId(), user.getNicOrPassport())).thenReturn(user);

        userService.deleteUser(user);

        Mockito.verify(userDAO, Mockito.times(1)).delete(user);
    }


    private User getUser_1() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        User user = new User();
        user.setUserId(1001);
        user.setNicOrPassport("971230720V");
        user.setFirstName("Nafaz");
        user.setLastName("Benzema");
        user.setEmail("nafazbenzema@gmail.com");
        user.setTeleNo("+97 7 164 5162");
        user.setCountry("Sri Lanka");
        user.setDob(dateFormat.parse("02-05-1997"));
        user.setRegisteredDate(new Date());
        user.setModifiedDate(new Date());

        return user;
    }

    private User getUser_2() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        User user = new User();
        user.setUserId(1002);
        user.setNicOrPassport("4544678633456UK");
        user.setFirstName("Kelly");
        user.setLastName("Brook");
        user.setEmail("brook@hotmail.com");
        user.setTeleNo("+32 87 765 5382");
        user.setCountry("United Kingdom");
        user.setDob(dateFormat.parse("22-10-1979"));
        user.setRegisteredDate(new Date());
        user.setModifiedDate(new Date());

        return user;
    }


    private List<User> getUsers() throws Exception {
        return new ArrayList<>(Arrays.asList(getUser_1(), getUser_2()));
    }
}

