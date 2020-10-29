package com.benz.user.api.service.impl;

import com.benz.user.api.dao.UserDAO;
import com.benz.user.api.entity.User;
import com.benz.user.api.entity.UserIdentity;
import com.benz.user.api.service.UserService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    @DisplayName("getUserTest")
    public void getUserTest()
    {
        User expectedUser=getUser();

        Mockito.when(userDAO.findUserByUserIdAndAndNicOrPassport(new UserIdentity(expectedUser.getUserId(),expectedUser.getNicOrPassport()))).thenReturn(Optional.of(expectedUser));

        User actualUser=userService.getUser(expectedUser);

        assertEquals(expectedUser,actualUser,"user should be returned");
    }

    @Test
    @DisplayName("getUsersTest")
    public void getUsersTest()
    {
        List<User> expectedUsers=getUsers();

        Mockito.when(userDAO.findAllUsers()).thenReturn(Optional.of(expectedUsers));

        List<User> actualUsers=userService.getUsers();

        assertEquals(expectedUsers,actualUsers,"The list of the user should be returned");

    }

    @Test
    @DisplayName("saveUserTest")
    public void saveUserTest()
    {
        User expectedUser=getUser();

       Mockito.when(userDAO.existsUserByUserIdAndNicOrPassport(new UserIdentity(expectedUser.getUserId(),expectedUser.getNicOrPassport()))).thenReturn(false);

        Mockito.when(userDAO.save(expectedUser)).thenReturn(expectedUser);

        User actualUser= userService.saveUser(expectedUser);

        assertEquals(expectedUser,actualUser,"saved user should be returned");
    }

    @Test
    @DisplayName("updateUserTest")
    public void updateUserTest()
    {
        User expectedUser=getUser();
        expectedUser.setCountry("Netherland");
        expectedUser.setNicOrPassport("199745356434NL");

        Mockito.when(userDAO.existsUserByUserIdAndNicOrPassport(new UserIdentity(expectedUser.getUserId(),expectedUser.getNicOrPassport())))
                .thenReturn(true);

        Mockito.when(userDAO.save(expectedUser)).thenReturn(expectedUser);

        User actualUser=userService.updateUser(expectedUser);

        assertEquals(expectedUser,actualUser,"updated user should be returned");

    }

    @Test
    @DisplayName("deleteUserTest")
    public void deleteUserTest()
    {
        User user=getUser();

        Mockito.when(userDAO.existsUserByUserIdAndNicOrPassport(new UserIdentity(user.getUserId(),user.getNicOrPassport()))).thenReturn(true);

        userService.deleteUser(user);

        verify(userDAO,times(1)).delete(user);
    }


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
    }

}
