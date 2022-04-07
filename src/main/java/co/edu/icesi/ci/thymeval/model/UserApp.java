package co.edu.icesi.ci.thymeval.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import co.edu.icesi.ci.thymeval.service.validate.AdvanceInformation;
import co.edu.icesi.ci.thymeval.service.validate.BasicInformation;
import co.edu.icesi.ci.thymeval.service.validate.UpdateInformation;
import lombok.Data;

@Entity
@Data
public class UserApp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(groups = {UpdateInformation.class,BasicInformation.class})
	@Size(min = 2,groups = {UpdateInformation.class,BasicInformation.class})
	private String name;

	@NotBlank(groups = {UpdateInformation.class,BasicInformation.class})
	@Email(groups = {UpdateInformation.class,BasicInformation.class})
	private String email;

	@NotNull(groups = {UpdateInformation.class,BasicInformation.class})
	private UserType type;

	@NotNull(groups = {UpdateInformation.class,AdvanceInformation.class})
	@Past(groups = {UpdateInformation.class,AdvanceInformation.class})
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	
	private UserGender gender;


	@NotBlank(groups ={UpdateInformation.class,AdvanceInformation.class})
	@NotNull(groups ={UpdateInformation.class,AdvanceInformation.class})
	@Size(min = 3)
	private String username;
	
	
	@NotBlank(groups =AdvanceInformation.class)
	@NotNull(groups =AdvanceInformation.class)
	@Size(min = 8,groups ={UpdateInformation.class,AdvanceInformation.class})
	private String password;
	
	
	//@Transient
	@Size(min = 8,groups =UpdateInformation.class)
	private String oldPassword;
	
	
	//@Transient
	@Size(min = 8,groups =UpdateInformation.class)
	private String newPassword;
	
	
	
//	@OneToMany
//	private List<Appointment> appointments;
}