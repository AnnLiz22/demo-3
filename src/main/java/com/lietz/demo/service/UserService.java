package com.lietz.demo.service;

import com.lietz.demo.model.User;
import com.lietz.demo.repo.UserRepo;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UserService {

  private UserRepo repo;

  @Autowired
  public void setRepo(UserRepo repo) {
    this.repo = repo;
  }

  public User addUser(User user) {
    repo.save(user);
    return user;
  }

  public User editUser(User user){
    System.out.println(user + " updated");
    return user;
  }

  public List<User> findAll(){
    return repo.findAll();
  }

  public void deleteUserByName(String userN){

    System.out.println(userN + " deleted");
  }
}
