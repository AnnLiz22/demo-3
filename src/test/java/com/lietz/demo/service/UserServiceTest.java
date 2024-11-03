package com.lietz.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lietz.demo.model.Task;
import com.lietz.demo.model.User;
import com.lietz.demo.repo.UserRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

  @Mock
  private UserRepo userRepo;

  @InjectMocks
  private UserService userService;

  List<User> users;

  @BeforeEach
  void setUp() {
    users = List.of(
        new User(1L, "Anna", "new role"),
        new User(2L, "Hanna", "my role"),
        new User(3L, "Krzys", "my super new role")
    );
  }

  @Test
  void shouldAddNewUser() {
    User user = new User(4L, "Anna", "mama");

    when(userRepo.save(user)).thenReturn(user);
    userService.addUser(user);
    assertEquals("Anna", user.getName());
    assertEquals("mama", user.getRole());

  }

  @Test
  void shouldGetUserById() {
    User user = users.get(0);
    when(userRepo.getById(user.getId())).thenReturn(Optional.of(user));

    userService.getUserById(user.getId());

    verify(userRepo).getById(user.getId());
  }

  @Test
  void shouldReturnUpdatedUserWhenUserExists() {
    User user = users.get(0);
    User updatedUser = new User(1L, "Ania", "same");

    when(userRepo.getById(1L)).thenReturn(Optional.of(user));
    when(userRepo.updateUser(user.getId(), user.getName(), user.getRole())).thenReturn(Optional.of(updatedUser));

    Optional<User> result = userService.updateUser(user.getId(), user.getName(), user.getRole(), user.getTasks());

    assertTrue(result.isPresent());
    assertEquals("Ania", result.get().getName());
    assertEquals("same", result.get().getRole());
    verify(userRepo).updateUser(user.getId(), user.getName(), user.getRole());
  }

  @Test
  void shouldReturnEmptyOptionalWhenUserDoesNotExist() {

    List <Task> userTasks = new ArrayList<>();
    when(userRepo.getById(10L)).thenReturn(null);

    Optional<User> user = userService.updateUser(10L, "Piotr", "role", userTasks);

    assertEquals(Optional.empty(), user);
    verify(userRepo, times(1)).updateUser(10L, "Piotr", "role");

  }


  @Test
  void shouldFindAllUsers() {

    when(userRepo.findAll()).thenReturn(users);
    List<User> givenUsers = userService.findAll();

    assertEquals(users, givenUsers);
    verify(userRepo, times(1)).findAll();

  }

  @Test
  void shouldDeleteUserByName() {

    userService.deleteUserByName(users.get(0).getName());
    userService.deleteUserByName(users.get(1).getName());
    verify(userRepo, times(1)).removeByName("Anna");
    verify(userRepo, times(1)).removeByName("Hanna");
  }

  @Test
  void shouldDeleteUserById() {

    userService.deleteUserById(1L);
    verify(userRepo, times(1)).removeById(1L);
  }

}