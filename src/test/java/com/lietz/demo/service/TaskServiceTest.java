package com.lietz.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lietz.demo.model.Task;
import com.lietz.demo.repo.TaskRepo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskServiceTest {
  @Mock
  private TaskRepo taskRepo;
  @InjectMocks
  private TaskService taskService;
  List<Task> tasks;

  @BeforeEach
  void setUp() {
    tasks = List.of(
        new Task(1L,"my task no 1", "to do asap", LocalDate.of(2024, 10,24), 1L),
        new Task(2L,"my task no 2", "to do", LocalDate.of(2024, 11,12), 1L),
        new Task(3L,"my task no 3", "extra task", LocalDate.of(2024, 12,16), 1L)
    );
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void shouldAddNewTask() {
    Task task = new Task("Task", "to do asap");
    when(taskRepo.save(task)).thenReturn(task);

    Task task1 = taskService.addTask(task);

    assertEquals(task1, task);
    verify(taskRepo, times(1)).save(task);
  }

  @Test
  void shouldFindTaskById() {
    Task task = tasks.get(0);

    when(taskRepo.getById(task.getId())).thenReturn(Optional.of(task));

    taskService.getById(task.getId());

    assertEquals(1L, task.getId());
    verify(taskRepo, times(1)).getById(task.getId());
  }

  @Test
  void shouldUpdateTaskIfTaskExists() {
    Task task = tasks.get(1);
    Task newTask = new Task(2L,"new task", "asap", LocalDate.of(2024, 11, 1), 1L);

    when(taskRepo.updateTask(task.getId(), task.getName(), task.getDescription(),task.getCreatedOn(), task.getUserId())).thenReturn(Optional.of(newTask));

   Optional<Task> task1 =  taskService.updateTask(task.getId(), task);

   assertTrue(task1.isPresent());
   assertEquals("asap", task1.get().getDescription());
   verify(taskRepo, times(1)).updateTask(task.getId(), task.getName(), task.getDescription(), task.getCreatedOn(), task.getUserId());

  }

  @Test
  void shouldFindAllTasks() {
    Task task = tasks.get(0);
    when(taskRepo.findAll()).thenReturn(tasks);

   List<Task> tasks = taskService.findAllTasks();

    assertTrue(tasks.contains(task));
    verify(taskRepo, times(1)).findAll();
  }

  @Test
  void deleteTask() {

    taskService.deleteTask(1L);
    verify(taskRepo, times(1)).removeById(1L);

  }
}