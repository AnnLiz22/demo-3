package com.lietz.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope(value = "prototype")
@Getter
@Setter
@ToString
public class User {

  private Integer id;
  private String name;
  private String role;

}
