package com.lietz.demo.controller;

import com.lietz.demo.model.User;
import com.lietz.demo.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public User addUser(@RequestBody User user){
   User addedUser = userService.addUser(user);
   return addedUser;
  }

  @RequestMapping(value = "/edit-user/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Optional<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
    Optional <User> existingUser = userService.getUserById(id);

    if (existingUser.isPresent()) {
      user.setId(id);
      Optional<User> updatedUser = userService.updateUser(id, user.getName(), user.getRole());
      return ResponseEntity.ok(updatedUser);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<User>> getAllUsers(){
    List<User> users = userService.findAll();
    if(users.isEmpty()){
      return ResponseEntity.noContent().build();
    }else{
      return ResponseEntity.ok(users);
    }
  }


  @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
  public ResponseEntity <User> deleteUser(@PathVariable Long id){
    Optional<User> optionalUser = userService.getUserById(id);
    if(optionalUser.isPresent()){
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
    }else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @RequestMapping(value = "/{userId}/assign-task/{taskId}", method = RequestMethod.PUT)
  public ResponseEntity<Optional<User>> assignTaskToUser(@PathVariable Long userId, @PathVariable Long taskId) {
    Optional<User> updatedUser = userService.assignTaskToUser(userId, taskId);
    return ResponseEntity.ok(updatedUser);
  }
}
