package co.edu.icesi.ci.thymeval.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.ci.thymeval.controller.interfaces.AppointmentController;
import co.edu.icesi.ci.thymeval.model.Appointment;
import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.service.interfaces.AppointmentService;
import co.edu.icesi.ci.thymeval.service.interfaces.UserService;
import co.edu.icesi.ci.thymeval.service.validate.AdvanceInformation;
import co.edu.icesi.ci.thymeval.service.validate.BasicInformation;

@Controller
public class AppointmentControllerImpl implements AppointmentController {

	AppointmentService appointmentService;
	UserService userService;

	@Autowired
	public AppointmentControllerImpl(AppointmentService appointmentService, UserService userService) {
		this.userService = userService;
		this.appointmentService = appointmentService;
		;
	}

	@GetMapping("/apps/add")
	public String addApp(Model model) {
		model.addAttribute("app", new Appointment());

		model.addAttribute("doctors", userService.findAllDoctors());
		model.addAttribute("patients", userService.findAllPatients());
		return "apps/add-app";
	}

	@GetMapping("/apps/del/{id}")
	public String deleteApp(@PathVariable("id") long id, Model model) {
		Appointment app = appointmentService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		appointmentService.delete(app);
		model.addAttribute("users", appointmentService.findAll());
		return "apps/index";
	}

	@GetMapping("/apps/")
	public String indexApp(Model model) {
		model.addAttribute("apps", appointmentService.findAll());
		return "apps/index";
	}

	@PostMapping("/apps/add")
	public String saveApp( @Validated @ModelAttribute Appointment app, BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			
		
		 if (bindingResult.hasErrors()) {
				return "/users/";
			 }
		 appointmentService.save(app);
		}
		return "redirect:/apps/";
		
		
	}

	@GetMapping("/apps/edit/{id}")
	public String showUpdateApp(@PathVariable("id") long id, Model model) {
		Optional<Appointment> app = appointmentService.findById(id);
		if (app == null)
			throw new IllegalArgumentException("Invalid appointment Id:" + id);
		model.addAttribute("app", app.get());

		model.addAttribute("doctors", userService.findAllDoctors());
		model.addAttribute("patients", userService.findAllPatients());
		return "apps/update-app";
	}

	@PostMapping("/apps/edit/{id}")
	public String updateApp(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			Appointment app,BindingResult bindingResult , Model model) {
		if (!action.equals("Cancel")) {
			appointmentService.save(app);
			model.addAttribute("apps", appointmentService.findAll());
		}
		
		 if (bindingResult.hasErrors()) {
				return "redirect:/users/";
			 }
		 
		return "redirect:/apps/";
	}
}
