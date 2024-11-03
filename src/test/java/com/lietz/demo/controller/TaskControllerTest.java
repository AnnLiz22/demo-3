package com.lietz.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lietz.demo.model.Task;
import com.lietz.demo.service.TaskService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private TaskService taskService;
  @Test
  void shouldFindTaskById() throws Exception {

    Task task = new Task(1L, "task", "to do", LocalDate.of(2024,12,12), 1l);

    when(taskService.getById(1L)).thenReturn(Optional.of(task));

    mockMvc.perform(get("/api/tasks/get-task/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"name\": \"task\", \"description\": \"to do\" }"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("task"))
        .andExpect(jsonPath("$.description").value("to do"));
  }
}