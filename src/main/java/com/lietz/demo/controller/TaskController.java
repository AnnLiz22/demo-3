package com.lietz.demo.controller;

import com.lietz.demo.model.Task;
import com.lietz.demo.service.TaskService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Task addTask(@RequestBody Task task){
    Task savedTask = taskService.addTask(task);
    return savedTask;
  }

  @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
    Optional<Task> updated = taskService.updateTask(id, updatedTask);

    if (updated.isPresent()) {
      return ResponseEntity.ok(updated.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
  public ResponseEntity <Task> deleteTask(@PathVariable Long id){
    Optional<Task> optionalTask = taskService.getById(id);
    if(optionalTask.isPresent()){
      taskService.deleteTask(id);
      return ResponseEntity.noContent().build();
    }else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<Task>> findTasks(){
    List<Task> tasks = taskService.findAllTasks();
    if(tasks.isEmpty()){
      return ResponseEntity.noContent().build();
    }else {
      return ResponseEntity.ok(tasks);
    }
  }

}