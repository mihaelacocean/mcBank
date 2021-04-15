package com.mcbank.service;

import com.mcbank.exception.AuthenticationException;
import com.mcbank.model.Account;
import com.mcbank.model.Credentials;
import com.mcbank.model.User;
import com.mcbank.persistence.UserRepository;
import com.mcbank.service.exception.UserNotFoundException;
import com.mcbank.service.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImpl userService;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void testCalculateUserBalance() throws UserNotFoundException {
    Mockito.when(userRepository.findById(1L)).thenReturn(mockUser(1L));
    User user = userService.getById(1L);
    Assert.assertEquals(132.5, user.getBalance(), 2);

  }

  @Test
  public void testLogin() throws UserNotFoundException {
    Mockito.when(userRepository.findByUsernameAndPassword(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(getUsersByFilter(1L));
    Credentials credentials = new Credentials();
    credentials.setUsername("admin");
    credentials.setPassword("admin");
    String authorizationHeader = userService.login(credentials);
    Assert.assertEquals("authorized", authorizationHeader);

  }

  @Test
  public void testLoginInvalidUser() throws UserNotFoundException {
    Mockito.when(userRepository.findByUsernameAndPassword(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(new ArrayList<>());

    exceptionRule.expect(UserNotFoundException.class);
    exceptionRule.expectMessage("Invalid credentials");
    Credentials credentials = new Credentials();
    credentials.setUsername("admin");
    credentials.setPassword("admin");
    userService.login(credentials);
  }

  @Test
  public void testAuthorize() {
    Mockito.when(userRepository.findByAuthorization("authorized")).thenReturn(getUsersByFilter(1L));
    User user = userService.authenticateUser("authorized");
    Assert.assertEquals(new Long(1L), user.getId());
  }

  @Test
  public void testAuthorizeInvalid() {
    Mockito.when(userRepository.findByAuthorization("authorized")).thenReturn(new ArrayList<>());
    exceptionRule.expect(AuthenticationException.class);
    exceptionRule.expectMessage("Invalid user");

    userService.authenticateUser("authorized");
  }

  Optional<User> mockUser(Long id) {
    User user = new User();
    user.setId(id);
    Account currentAccount = new Account();
    currentAccount.setId(1L);
    currentAccount.setAmount(100);
    Account savings = new Account();
    savings.setId(2L);
    savings.setAmount(32.5);
    user.getAccounts().add(currentAccount);
    user.getAccounts().add(savings);
    return Optional.ofNullable(user);
  }

  List<User> getUsersByFilter(Long id) {
    User user = new User();
    user.setId(id);
    user.setAuthorization("authorized");
    List<User> users = new ArrayList<>();
    users.add(user);
    return users;
  }
}
