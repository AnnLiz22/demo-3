package com.lietz.demo.repo;

import com.lietz.demo.model.User;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Setter
@Getter
@RequiredArgsConstructor
public class UserRepo {
  private final JdbcTemplate template;

  public void save(User user) {
    String query = "INSERT INTO users (id, name, role) VALUES (?,?,?)";
    int rows = template.update(query, user.getId(), user.getName(), user.getRole());
    System.out.println(rows + " effectuated");
  }

  public User getById(Long id) {
    String query = "SELECT * from users where id = ?";

    RowMapper<User> mapper = (rs, rowNum) -> {
      User user = new User();
      user.setId(rs.getLong("id"));
      user.setName(rs.getString("name"));
      user.setRole(rs.getString("role"));
      return user;
    };
    return template.queryForObject(query, new Object[] {id}, mapper);
  }

  public int updateUser(User user) {
    String query = "UPDATE users SET name = ?, role = ? WHERE id = ?";
    return template.update(query, user.getName(), user.getRole(), user.getId());
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

  }

  public void removeById(Long id) {
    String query = "DELETE from users where id = ?";

  }
}

