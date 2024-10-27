package com.lietz.demo.controller;

import com.lietz.demo.model.User;
import com.lietz.demo.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @RequestMapping(value = "/get-user/{id}", method = RequestMethod.GET)
  public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
    Optional<User> user = userService.getUserById(id);
    if (user.isPresent()) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @RequestMapping(value = "/edit-user/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Optional<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
    Optional <User> existingUser = userService.getUserById(id);

    if (existingUser.isPresent()) {
      user.setId(id);
      Optional<User> updatedUser = userService.updateUser(id);
      return ResponseEntity.ok(updatedUser);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String getAllUsers(){
    return "Here are all users!";
  }

}
