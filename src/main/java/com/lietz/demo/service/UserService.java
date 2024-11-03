package com.lietz.demo.service;

import com.lietz.demo.model.Task;
import com.lietz.demo.model.User;
import com.lietz.demo.repo.UserRepo;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class UserService {
  private final UserRepo repo;

  public User addUser(User user) {
    repo.save(user);
    return user;
  }

  public Optional<User> getUserById(Long id){
    return repo.getById(id);
  }

  public Optional<User> updateUser(Long id, String name, String role, List<Task> tasks){
    return repo.updateUser(id, name, role);
  }

  public List<User> findAll(){
    return repo.findAll();
  }

  public void deleteUserByName(String userName){
    repo.removeByName(userName);
  }

  public void deleteUserById(Long id){
    repo.removeById(id);
  }

  public Optional<User> assignTaskToUser(Long userId, Long taskId) {
    Optional<User> user = getUserById(userId);
    repo.assignTaskToUser(userId, taskId);
    return user;
  }

  public List<User> findAllUsersWithTasks() {
    return repo.findAllUsersWithTasks();
  }
}
