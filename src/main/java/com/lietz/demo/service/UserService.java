package com.lietz.demo.service;

import com.lietz.demo.model.User;
import com.lietz.demo.repo.UserRepo;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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

  public User getUserById(Long id){
    return repo.getById(id);
  }

  public int editUser(User user){
    System.out.println(user + " updated");
    return repo.updateUser(user);
  }

  public List<User> findAll(){
    return repo.findAll();
  }

  public void deleteUserByName(String userN){

    System.out.println(userN + " deleted");
  }
}
