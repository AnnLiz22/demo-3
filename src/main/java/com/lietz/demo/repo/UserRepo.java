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

  public void save(User user) {
    String query = "INSERT INTO users (id, name, role) VALUES (?,?,?)";
    int rows = template.update(query, user.getId(), user.getName(), user.getRole());
    System.out.println(rows + " effectuated");
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
  public Optional<User> updateUser(Long id) {
    String query = "UPDATE users SET name = ?, role = ? WHERE id = ?";
    RowMapper<User> mapper = (rs, rowNum) -> {
      Optional<User> user = getById(id);
      if (user.isPresent()) {

        User user1 = new User();
        user1.setId(rs.getLong("id"));
        user1.setName(rs.getString("name"));
        user1.setRole(rs.getString("role"));
        return user1;
      }
      return null;
    };

      return Optional.ofNullable(template.queryForObject(query, new Object[] {id}, mapper));
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

