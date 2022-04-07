package co.edu.icesi.ci.thymeval;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.model.UserGender;
import co.edu.icesi.ci.thymeval.model.UserType;
import co.edu.icesi.ci.thymeval.repository.UserRepository;
import co.edu.icesi.ci.thymeval.service.UserServiceImpl;
import co.edu.icesi.ci.thymeval.service.validate.AdvanceInformation;
import co.edu.icesi.ci.thymeval.service.validate.BasicInformation;
import co.edu.icesi.ci.thymeval.service.validate.UpdateInformation;

@SpringBootApplication
public class ThymeleafValidationApplication {

	//private static UserServiceImpl usersRepo = new UserServiceImpl();

	@Bean

	public CommandLineRunner dummy(UserRepository userRepository) {

		//para cerrar sesion:
		//http://localhost:8081/logout
		return (args) -> {
			UserServiceImpl registration = new UserServiceImpl(userRepository);
	    	UserApp u = new UserApp();
	    	u.setPassword("{noop}1234567");
	    	u.setEmail("example@gmail.com");
	    	u.setName("Peppa pig");
	    	u.setType(UserType.admin);
	    	u.setBirthDate(LocalDate.now());
	    	u.setGender(UserGender.femenine);
	    	u.setUsername("peppa1234");
	    	//u.setOldPassword("{noop}123456789");
	    	registration.save(u);
	    	
	    	
	    	UserApp u2 = new UserApp();
	    	u2.setPassword("{noop}1234");
	    	u2.setEmail("example@gmail.com");
	    	u2.setName("Dora");
	    	u2.setType(UserType.doctor);
	    	u2.setBirthDate(LocalDate.now());
	    	u2.setGender(UserGender.femenine);
	    	u2.setUsername("dora1234");
	 	    	
			
			registration.save(u2);
		};


	}


	// Alejandra Diaz Parra - Sebastian Morales
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(ThymeleafValidationApplication.class, args);
	}

}
