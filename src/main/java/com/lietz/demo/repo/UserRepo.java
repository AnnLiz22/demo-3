package com.lietz.demo.repo;

import com.lietz.demo.model.Task;
import com.lietz.demo.model.User;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Setter
@Getter
@NoArgsConstructor
public class UserRepo {
  @Autowired
  private JdbcTemplate template;
  @Autowired
  private TaskRepo taskRepo;
  private static final Logger logger = LoggerFactory.getLogger(UserRepo.class);

  public User save(User user) {
    String query = "INSERT INTO users (id, name, role) VALUES (?,?,?)";
    int rows = template.update(query, user.getId(), user.getName(), user.getRole());
    logger.info(rows + " effectuated");
    return user;
  }

  public Optional<User> getById(Long id) {
    String query = "SELECT * from users where id = ?";

    RowMapper<User> mapper = (rs, rowNum) -> {
      User user = new User();
      user.setId(rs.getLong("id"));
      user.setName(rs.getString("name"));
      user.setRole(rs.getString("role"));

      logger.info("getting user with id " + id);
      return user;
    };
    try {
      return Optional.ofNullable(template.queryForObject(query, mapper, id));
    } catch (EmptyResultDataAccessException e) {
      logger.info("user with given id not found");
      return Optional.empty();
    }
  }

  public Optional<User> updateUser(Long id, String newName, String newRole) {
    String query = "UPDATE users SET name = ?, role = ? WHERE id = ?";
    int rowsAffected = template.update(query, newName, newRole, id);

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
      logger.info("User with name " + userName + " was successfully deleted.");
    } else {
      logger.info("No user found with name " + userName);
    }
  }

  public void removeById(Long id) {
    String query = "DELETE from users where id = ?";
    int rowsAffected = template.update(query, id);

    if (rowsAffected > 0) {
      logger.info("User with id " + id + " was successfully deleted.");
    } else {
      logger.info("No user found with id " + id);
    }
  }

  @Transactional
  public void assignTaskToUser(Long userId, Long taskId) {
    Optional<User> userOptional = getById(userId);
    Optional<Task> taskOptional = taskRepo.getById(taskId);

    if (taskOptional.isPresent() && userOptional.isPresent()) {
      User user = userOptional.get();
      Task task = taskOptional.get();

      task.setUserId(userId);
      taskRepo.updateTask(task.getId(), task.getName(), task.getDescription(), task.getCreatedOn(), task.getUserId());

      user.setTasks(taskRepo.getTasksByUserId(userId));
      logger.info("task "+ taskId + " successfully assigned to user " + userId);
    }

  }

  public List<User> findAllUsersWithTasks() {
    String query = "SELECT u.id AS user_id, u.name AS user_name, u.role AS user_role, "
        + "t.id AS task_id, t.name AS task_name, t.description AS task_description, t.createdOn AS task_createdOn "
        + "FROM users u "
        + "LEFT JOIN tasks t ON u.id = t.user_id";

    return template.query(query, rs -> {
      Map<Long, User> userMap = new HashMap<>();

      while (rs.next()) {
        Long userId = rs.getLong("user_id");
        User user = userMap.get(userId);

        if (user == null) {
          user = new User();
          user.setId(userId);
          user.setName(rs.getString("user_name"));
          user.setRole(rs.getString("user_role"));
          user.setTasks(new ArrayList<>());
          userMap.put(userId, user);
        }

        Long taskId = rs.getLong("task_id");
        if (taskId != 0) {
          Task task = new Task();
          task.setId(taskId);
          task.setName(rs.getString("task_name"));
          task.setDescription(rs.getString("task_description"));
          task.setCreatedOn(rs.getDate("task_createdOn").toLocalDate());
          user.getTasks().add(task);
        }
      }
      logger.info("getting all users with tasks " );
      return new ArrayList<>(userMap.values());
    });
  }
}

