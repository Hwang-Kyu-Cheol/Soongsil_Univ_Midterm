package midterm;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import midterm.controller.UserController;
import midterm.exception.Validator;
import midterm.repository.UserRepository;
import midterm.service.UserService;

@Configuration
@ComponentScan(basePackages= {"midterm"})
public class AppContext {
	
	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}
}
