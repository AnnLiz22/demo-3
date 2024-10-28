package com.lietz.demo.repo;

import com.lietz.demo.model.User;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Setter
@Getter
@RequiredArgsConstructor
public class UserRepo {
  private final JdbcTemplate template;

  public User save(User user) {
    String query = "INSERT INTO users (id, name, role) VALUES (?,?,?)";
    int rows = template.update(query, user.getId(), user.getName(), user.getRole());
    System.out.println(rows + " effectuated");
    return user;
  }

  public Optional<User> getById(Long id) {
    String query = "SELECT * from users where id = ?";

    RowMapper<User> mapper = (rs, rowNum) -> {
      User user = new User();
      user.setId(rs.getLong("id"));
      user.setName(rs.getString("name"));
      user.setRole(rs.getString("role"));
      return user;
    };
    try {
      return Optional.ofNullable(template.queryForObject(query, new Object[] {id}, mapper));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
  public Optional<User> updateUser(Long id, String newName, String newRole) {
    String query = "UPDATE users SET name = ?, role = ? WHERE id = ?";
    int rowsAffected = template.update(query, newName, newRole, id);

    Optional<User> user = getById(id);
    if (user.isEmpty()) {
      return Optional.empty();
    }

    if (rowsAffected > 0) {
      return getById(id);
    } else {
      return Optional.empty();
    }
  }

  public List<User> findAll() {
    String query = "SELECT * from users";

    RowMapper<User> mapper = (rs, rowNum) -> {

      User user = new User();
      user.setId(rs.getLong("id"));
      user.setName(rs.getString("name"));
      user.setRole(rs.getString("role"));

      return user;
    };
    return template.query(query, mapper);
  }

  public void removeByName(String userName) {
    String query = "DELETE from users where name = ?";
    int rowsAffected = template.update(query, userName);

    if (rowsAffected > 0) {
      System.out.println("User with name " + userName + " was successfully deleted.");
    } else {
      System.out.println("No user found with name " + userName);
    }

  }

  public void removeById(Long id) {
    String query = "DELETE from users where id = ?";
    int rowsAffected = template.update(query, id);

    if (rowsAffected > 0) {
      System.out.println("User with id " + id + " was successfully deleted.");
    } else {
      System.out.println("No user found with id " + id);
    }
  }
}

