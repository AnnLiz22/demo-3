package com.lietz.demo.service;

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

  public Optional<User> updateUser(Long id){
  //  System.out.println(user + " updated");
    return repo.updateUser(id);
  }

  public List<User> findAll(){
    return repo.findAll();
  }

  public void deleteUserByName(String userName){
    repo.removeByName(userName);
    System.out.println("User with name " + userName + " deleted");
  }

  public void deleteUserById(Long id){
    repo.removeById(id);
    System.out.println("User with id " + id + " deleted");

  }
}
