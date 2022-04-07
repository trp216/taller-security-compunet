package co.edu.icesi.ci.thymeval.controller.implementation;

import java.io.Console;
import java.text.MessageFormat.Field;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.ci.thymeval.controller.interfaces.UserController;
import co.edu.icesi.ci.thymeval.model.Appointment;
import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.service.UserServiceImpl;
import co.edu.icesi.ci.thymeval.service.validate.AdvanceInformation;
import co.edu.icesi.ci.thymeval.service.validate.BasicInformation;
import co.edu.icesi.ci.thymeval.service.validate.UpdateInformation;

@Controller
public class UserControllerImpl implements UserController {

	UserServiceImpl userService;

	@Autowired
	public UserControllerImpl(UserServiceImpl userService) {
		this.userService = userService;
		;
	}

	@GetMapping("/users/del/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		UserApp user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.delete(user);
		model.addAttribute("users", userService.findAll());
		return "users/index";
	}

	@GetMapping("/users/")
	public String indexUser(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users/index";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("users", userService.findAll());
		return "/login";
	}

	@GetMapping("/users/add")
	public String addUser(Model model) {
		model.addAttribute("user", new UserApp());
		model.addAttribute("genders", userService.getGenders());
		model.addAttribute("types", userService.getTypes());

		return "users/add-user1";
	}

	@PostMapping("/users/add/")
	public String saveUser(@Validated(AdvanceInformation.class) @ModelAttribute UserApp user, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {

			model.addAttribute("user", user);
			model.addAttribute("genders", userService.getGenders());
			model.addAttribute("types", userService.getTypes());

			if (bindingResult.hasErrors()) {

				return "/users/add-user1";

			}

			// model.addAttribute("username",user.getUsername());

		}
		return "/users/add-user2";
	}

	@PostMapping("/users/add/users2")
	public String saveUser2(@Validated(BasicInformation.class) @ModelAttribute UserApp user, BindingResult bindingResult,
			Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			model.addAttribute("user", user);
			model.addAttribute("genders", userService.getGenders());
			model.addAttribute("types", userService.getTypes());

			if (bindingResult.hasErrors()) {

				return "/users/add-user2";

			}

			userService.save(user);
		}
		return "redirect:/users/";
	}

	@GetMapping("/users/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Optional<UserApp> user = userService.findById(id);
		if (user == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("user", user.get());
		model.addAttribute("genders", userService.getGenders());
		model.addAttribute("types", userService.getTypes());
		return "users/update-user";
	}

	@PostMapping("/users/edit/{id}")
	public String updateUser(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated(UpdateInformation.class) @ModelAttribute UserApp user, BindingResult bindingResult, Model model) {

		if (action != null && !action.equals("Cancel")) {

			if (user.getNewPassword() == null && user.getOldPassword() == null) { // Si no se ha llenado el campo de new
																					// password
				user.setPassword(userService.findById(id).get().getPassword());
				userService.save(user);
			} else {
				if (userService.findById(id).get().getPassword().equals(user.getOldPassword())) { // si el old password
					if (user.getNewPassword().equals(userService.findById(id).get().getPassword())) {
						bindingResult.addError(
								new FieldError("user", "newPassword", "las contraseñas no pueden ser iguales "));

					} else {
						user.setPassword(user.getNewPassword());
						userService.save(user);
						model.addAttribute("users", userService.findAll());

					}
				} else {
					// tire mensaje de error si no coincide el old password del html con el de la
					// base de datos
					bindingResult.addError(new FieldError("user", "oldPassword",
							"error contraseña no coincide con la base de datos "));

				}
				if (bindingResult.hasErrors()) {

					model.addAttribute("user", user);
					model.addAttribute("genders", userService.getGenders());
					model.addAttribute("types", userService.getTypes());

					return "/users/update-user";

				}
			}

		}
		return "redirect:/users/";
	}

}
