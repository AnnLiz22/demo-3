package com.lietz.demo;

import com.lietz.demo.model.User;
import com.lietz.demo.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		System.out.println("hello there");
		User user = context.getBean(User.class);
		UserService service = context.getBean(UserService.class);
	}
}