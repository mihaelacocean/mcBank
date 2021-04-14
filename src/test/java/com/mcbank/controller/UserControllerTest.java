package com.mcbank.controller;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.mcbank.exception.AuthenticationException;
import com.mcbank.model.Account;
import com.mcbank.model.User;
import com.mcbank.service.AccountService;
import com.mcbank.service.TransactionService;
import com.mcbank.service.UserService;
import com.mcbank.service.exception.UserNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = com.mcbank.controller.UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private AccountService accountService;

  @MockBean
  private TransactionService transactionService;

  @Test
  public void testCreateAccount() throws Exception {

    when(accountService.openCurrentAccount(anyLong(), anyDouble()))
        .thenReturn(getAccount(1L));
    when(userService.userExists(Mockito.anyLong())).thenReturn(true);
    doNothing().when(transactionService).addMoney(anyLong(), anyDouble());
    when(userService.authenticateUser("authorized")).thenReturn(getAdmin());

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
        "/users/1/accounts/current").contentType(
        MediaType.APPLICATION_JSON).header("Authorization", "authorized").
        content("34");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    Assert.assertEquals(200, result.getResponse().getStatus());
    Assert.assertEquals("1", result.getResponse().getContentAsString());
  }

  @Test
  public void testCreateAccountNotAuthenticated() throws Exception {
    when(userService.authenticateUser("notAuthenticated"))
        .thenThrow(new AuthenticationException("Invalid user"));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
        "/users/1/accounts/current").contentType(
        MediaType.APPLICATION_JSON).header("Authorization", "notAuthenticated").
        content("34");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    Assert.assertEquals(401, result.getResponse().getStatus());
  }

  @Test
  public void testCreateAccountNotAuthorized() throws Exception {
    when(userService.authenticateUser("authenticated")).thenReturn(getUser(1L));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
        "/users/1/accounts/current").contentType(
        MediaType.APPLICATION_JSON).header("Authorization", "authenticated").
        content("34");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    Assert.assertEquals(403, result.getResponse().getStatus());
  }

  @Test
  public void testGetUser() throws Exception {
    when(userService.authenticateUser("authenticated")).thenReturn(getUser(1L));
    Mockito.when(userService.getById(1L)).thenReturn(getUser(1L));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/users/1").accept(
        MediaType.APPLICATION_JSON).header("Authorization", "authenticated");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    String expectedResult = "{\"id\":1,\"name\":\"Name1\",\"surname\":\"Surname1\",\"balance\":0.0,\"accounts\":[]}";
    Assert.assertEquals(200, result.getResponse().getStatus());
    Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
  }

  @Test
  public void testGetUserAdmin() throws Exception {

    Mockito.when(userService.getById(1L)).thenReturn(getUser(1L));
    when(userService.authenticateUser("authorized")).thenReturn(getAdmin());
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/users/1").accept(
        MediaType.APPLICATION_JSON).header("Authorization", "authorized");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    String expectedResult = "{\"id\":1,\"name\":\"Name1\",\"surname\":\"Surname1\",\"balance\":0.0,\"accounts\":[]}";
    Assert.assertEquals(200, result.getResponse().getStatus());
    Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
  }

  @Test
  public void testGetUserInvalidUserId() throws Exception {
    when(userService.authenticateUser("authenticated")).thenReturn(getUser(1L));
    Mockito.when(userService.getById(1L)).thenReturn(getUser(1L));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/users/2").accept(
        MediaType.APPLICATION_JSON).header("Authorization", "authenticated");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    Assert.assertEquals(403, result.getResponse().getStatus());
  }

  @Test
  public void testLogin() throws Exception {

    when(userService.login(Mockito.any())).thenReturn("authorized");
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
        "/users/login").contentType(
        MediaType.APPLICATION_JSON).content("{\"username\":\"admin\", \"password\": \"admin\"}");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    Assert.assertEquals(200, result.getResponse().getStatus());
    Assert.assertEquals("authorized", result.getResponse().getContentAsString());
  }

  @Test
  public void testLoginInvalidUser() throws Exception {

    when(userService.login(Mockito.any())).thenThrow(new UserNotFoundException());
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
        "/users/login").contentType(
        MediaType.APPLICATION_JSON).content("{\"username\":\"admin\", \"password\": \"admin\"}");

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    Assert.assertEquals(401, result.getResponse().getStatus());
  }

  private User getUser(Long id) {
    User user = new User();
    user.setName("Name" + id);
    user.setSurname("Surname" + id);
    user.setId(id);
    return user;
  }

  private Account getAccount(Long accountId) {
    Account account = new Account();
    account.setId(accountId);
    return account;
  }

  private User getAdmin() {
    User user = new User();
    user.setId(111L);
    user.setAdmin(true);
    return user;
  }

}
