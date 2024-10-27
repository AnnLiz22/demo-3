package com.lietz.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope(value = "prototype")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private Long id;
  private String name;
  private String role;

}
