package com.lietz.demo;

import com.lietz.demo.model.User;
import com.lietz.demo.repo.UserRepo;
import com.lietz.demo.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		System.out.println("hello there");

		User user = context.getBean(User.class);
		user.setId(7);
		user.setName("Hanna");
		user.setRole("mama");

		UserService service = context.getBean(UserService.class);
		service.addUser(user);


		System.out.println(service.findAll());
		service.editUser(user);
		service.deleteUserByName(user.getName());
	}

}
