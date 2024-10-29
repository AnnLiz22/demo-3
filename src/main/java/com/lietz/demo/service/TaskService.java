package com.lietz.demo.service;

import com.lietz.demo.model.Task;
import com.lietz.demo.repo.TaskRepo;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepo taskRepo;


  public Task addTask(Task task){

    taskRepo.save(task);
     return task;
  }

  public Optional<Task> getById(Long id){
    return taskRepo.getById(id);
  }

  public Optional<Task> updateTask(Long id, Task task){
    return taskRepo.updateTask(id, task.getName(), task.getDescription(), task.getCreatedOn());
  }

  public List<Task> findAllTasks(){
    return taskRepo.findAll();
  }

  public void deleteTask(Long id){
    taskRepo.removeById(id);
  }
}
