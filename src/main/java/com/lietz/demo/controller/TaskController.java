package com.lietz.demo.controller;

import com.lietz.demo.model.Task;
import com.lietz.demo.model.User;
import com.lietz.demo.service.TaskService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @RequestMapping(value = "/get-task/{id}")
  public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Long id) {
    Optional<Task> task = taskService.getById(id);
    if (task.isPresent()) {
      return ResponseEntity.ok(task);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}