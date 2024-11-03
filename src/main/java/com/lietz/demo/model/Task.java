package com.lietz.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private LocalDate createdOn;
  private Long userId;

  public Task(String name, String description, LocalDate createdOn, Long userId) {
    this.name = name;
    this.description = description;
    this.createdOn = createdOn;
    this.userId = userId;
  }

  public Task(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
