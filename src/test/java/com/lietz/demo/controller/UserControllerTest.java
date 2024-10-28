package com.lietz.demo.controller;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lietz.demo.model.User;
import com.lietz.demo.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;
  @Test
  void getUserById() {
  }

  @Test
  void shouldReturnOkStatusAndUpdatedUserWhenUserExists() throws Exception {
    User user = new User(1L, "Anna", "my role");
    User updatedUser = new User(1L, "Ania", "my new role");

    when(userService.getUserById(1L)).thenReturn(Optional.of(user));
    when(userService.updateUser(1L, user.getName(), user.getRole())).thenReturn(Optional.of(updatedUser));

    mockMvc.perform(put("/api/users/edit-user/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"name\": \"Ania\", \"role\": \"my new role\" }"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Ania"))
        .andExpect(jsonPath("$.role").value("my new role"));
  }

  @Test
  public void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
    when(userService.getUserById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(put("/api/users/edit-user/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"name\": \"Ania\", \"role\": \"my new role\" }"))
        .andExpect(status().isNotFound());
  }

  @Test
  void getAllUsers() {
  }
}