package co.edu.icesi.ci.thymeval.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import co.edu.icesi.ci.thymeval.service.validate.BasicInformation;
import lombok.Data;

@Entity
@Data
public class Appointment {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	
	
	@NotBlank
	@Size(min = 2)
	private String name;
	
	@NotBlank
	@FutureOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	
	private LocalDate date;
	
	@NotNull
	@FutureOrPresent
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime time;
	
	
	@ManyToOne
	private UserApp patient;
	
	@ManyToOne
	private UserApp doctor;
}
