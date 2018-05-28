package pl.speechrecognition.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.speechrecognition.forms.LoginForm;
import pl.speechrecognition.forms.RegisterForm;
import pl.speechrecognition.model.User;
import pl.speechrecognition.services.CloudService;

@Controller
public class IndexController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CloudService cloudService;

	@GetMapping("/")
	public ModelAndView index(LoginForm loginForm, @RequestParam(required = false) String error) {
		ModelAndView model = new ModelAndView("index");
		if (error != null && error.equals("true"))
			model.addObject("errorLogin", true);
		return model;
	}
	
	@GetMapping("/register")
	public String getRegister(RegisterForm registerForm) {
		return "register";
	}

	@PostMapping("/register")
	public ModelAndView postRegister(@Valid RegisterForm registerForm, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		model.setViewName("register");
		if (bindingResult.hasErrors()) {
			return model;
		}
		if (cloudService.sendRequestIfUserExists(registerForm.getUsername())) {
			model.addObject("errorUserExists", true);
			return model;
		}
		if (!registerForm.getPassword().equals(registerForm.getRepeatPassword())) {
			model.addObject("errorRepeat", true);
			return model;
		}

		User user = new User(registerForm.getUsername(), passwordEncoder.encode(registerForm.getPassword()));
		cloudService.sendRequestSaveUser(user);

		model.setViewName("redirect:/");
		return model;
	}

	@GetMapping("/logout")
	public String logout(LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		return "redirect:/";
	}
}
