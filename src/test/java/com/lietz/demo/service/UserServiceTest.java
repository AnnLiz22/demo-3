package com.lietz.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lietz.demo.model.User;
import com.lietz.demo.repo.UserRepo;
import java.util.Optional;
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

  @Test
  void getUserById() {
  }

  @Test
  void shouldReturnUpdatedUserWhenUserExists() {
    User user = new User(1L, "Anna", "mama");
    User updatedUser = new User(1L, "Ania", "same");

    when(userRepo.getById(1L)).thenReturn(Optional.of(user));
    when(userRepo.updateUser(updatedUser.getId())).thenReturn(Optional.of(updatedUser));

    Optional<User> result = userService.updateUser(updatedUser.getId());

    assertTrue(result.isPresent());
    assertEquals("Ania", result.get().getName());
    assertEquals("same", result.get().getRole());
    verify(userRepo).updateUser(user.getId());
  }
}