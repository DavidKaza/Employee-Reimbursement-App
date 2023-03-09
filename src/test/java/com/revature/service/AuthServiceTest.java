package com.revature.service;

import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.MissingRequiredFieldException;
import com.revature.exceptions.model.User;
import com.revature.repository.UserRepository;
import com.revature.exceptions.InvalidLoginException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService as;


    @Test
    public void testAuthLoginUserNotRegisteredOrWrongPassword() throws SQLException, InvalidLoginException {
        //Arrange
        //Mocking a response from userRepository
        when(userRepository.getUserByUsernameAndPassword(eq("Joe"), eq("Shmoe"))).thenReturn(null);
        //Act + Assert
        Assertions.assertThrows(InvalidLoginException.class, () -> {
           as.login("Joe", "Shmoe");
        });
    }
    @Test
    public void testAuthLoginPositive() throws SQLException, InvalidLoginException {
        //Arrange
        when(userRepository.getUserByUsernameAndPassword(eq("John"), eq("password"))).thenReturn(new User(1, "John", "password","John", "Doe", 1));
        //Act
        User user = as.login("John", "password");

        //Assert
        Assertions.assertEquals(new User(1, "John", "password", "John", "Doe", 1), user);
    }
    @Test
    public void testRegisterExistingUsername() throws SQLException {
        when(userRepository.getUserInfo("John")).thenReturn(new User("John", "password", "John", "Doe"));
        Assertions.assertThrows(ExistingUserException.class, ()-> {
            as.register(new User("John", "password", "John", "Doe"));
        });
    }
    @Test
    public void testRegisterMissingFields() throws SQLException {
        Assertions.assertThrows(MissingRequiredFieldException.class, ()-> {
            as.register(new User("John", "", "John", "Doe"));
        });
    }
    @Test
    public void testRegisterPositive() throws SQLException, ExistingUserException, MissingRequiredFieldException {
        when(userRepository.getUserInfo("Randy")).thenReturn(null);
        when(userRepository.addUser(new User("Randy", "password", "Randy", "Marsh"))).thenReturn(new User(1,"Randy", "password", "Randy", "Marsh", 1));

        User user = as.register(new User("Randy", "password", "Randy", "Marsh"));

        Assertions.assertEquals(new User(1,"Randy", "password", "Randy", "Marsh", 1), user);
    }
}
