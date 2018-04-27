package pl.speechrecognition.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.speechrecognition.data.UserRepository;
import pl.speechrecognition.forms.LoginForm;
import pl.speechrecognition.forms.RegisterForm;
import pl.speechrecognition.model.User;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/test")
	public void test() {
		userRepository.save(new User("test", passwordEncoder.encode("test")));
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/processLogin")
	public ModelAndView processLogin(@Valid LoginForm loginForm, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		if (bindingResult.hasErrors()) {
			model.setViewName("register");
			return model;
		}

		model.setViewName("redirect:/home");
		return model;
	}

	@GetMapping("/register")
	public String getRegister(RegisterForm registerForm) {
		return "register";
	}

	@PostMapping("/register")
	public ModelAndView postRegister(@Valid RegisterForm registerForm, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		if (bindingResult.hasErrors()) {
			model.setViewName("register");
			return model;
		}
		if (userRepository.existsByUsername(registerForm.getUsername())) {
			model.setViewName("register");
			model.addObject("errorUserExists", true);
			return model;
		}

		if (registerForm.getPassword().equals(registerForm.getRepeatPassword())) {
			model.setViewName("register");
			model.addObject("errorRepeat", true);
			return model;
		}

		User user = new User(registerForm.getUsername(), passwordEncoder.encode(registerForm.getPassword()));
		userRepository.save(user);

		model.setViewName("index");
		return model;
	}

	@PostMapping("/logout")
	public String logout(@Valid LoginForm loginForm, BindingResult bindingResult) {
		return "index";
	}

}
