package com.lietz.demo.repo;

import com.lietz.demo.model.Task;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskRepo {

  private final JdbcTemplate template;
  private static final Logger logger = LoggerFactory.getLogger(TaskRepo.class);

  public Task save(Task task) {
    String query = "INSERT INTO tasks (id, name, role, createdOn, user_id) VALUES (?, ?, ?, ?, ?)";
    int rows = template.update(query, task.getId(), task.getName(), task.getDescription(), task.getCreatedOn());
    logger.info(rows + " effectuated");
    return task;
  }

  public Optional<Task> getById(Long id) {
    String query = "SELECT * from tasks where id = ?";

    RowMapper<Task> mapper = (rs, rowNum) -> {
      Task task = new Task();
      task.setId(rs.getLong("id"));
      task.setName(rs.getString("name"));
      task.setDescription(rs.getString("description"));
      task.setCreatedOn(rs.getDate("createdOn").toLocalDate());
      task.setUserId(rs.getLong("user_id"));
      logger.info("Selected task with id " + id);
      return task;
    };
    try {
      return Optional.ofNullable(template.queryForObject(query, new Object[] {id}, mapper));
    } catch (EmptyResultDataAccessException e) {
      logger.info("task with id " + id + " not found");
      return Optional.empty();
    }
  }


  public Optional<Task> updateTask(Long id, String newName, String newDescription, LocalDate newCreatedOn, Long userId) {
    String query = "UPDATE tasks SET name = ?, description = ?, createdOn = ?, user_id = ? WHERE id = ?";
    int rowsAffected = template.update(query, newName, newDescription, newCreatedOn, userId, id);

    Optional<Task> task = getById(id);
    if (task.isEmpty()) {
      logger.info("Task not found.");
      return Optional.empty();
    }

    if (rowsAffected > 0) {
      return getById(id);
    } else {
      return Optional.empty();
    }
  }

  public List<Task> findAll() {
    String query = "SELECT * from tasks";

    RowMapper<Task> mapper = (rs, rowNum) -> {

      Task task = new Task();
      task.setId(rs.getLong("id"));
      task.setName(rs.getString("name"));
      task.setDescription(rs.getString("description"));
      task.setCreatedOn(rs.getDate("createdOn").toLocalDate());
      task.setUserId(rs.getLong("user_id"));

      return task;
    };
    logger.info("get all tasks");
    return template.query(query, mapper);
  }

  public void removeById(Long id) {
    String query = "DELETE from tasks where id = ?";
    int rowsAffected = template.update(query, id);

    if (rowsAffected > 0) {
      logger.info("Task with id " + id + " was successfully deleted.");
    } else {
      logger.info("No task found with id " + id);
    }
  }

  public List<Task> getTasksByUserId(Long userId) {
    String sql = "SELECT * FROM tasks WHERE user_id = ?";
    return template.query(sql, new Object[]{userId}, (rs, rowNum) -> {
      Task task = new Task();
      task.setId(rs.getLong("id"));
      task.setName(rs.getString("name"));
      task.setDescription(rs.getString("description"));
      task.setCreatedOn(rs.getDate("createdOn").toLocalDate());
      task.setUserId(rs.getLong("user_id"));

      logger.info("Get all tasks from user " + userId);
      return task;
    });
  }
}
