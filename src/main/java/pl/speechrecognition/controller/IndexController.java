package pl.speechrecognition.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.speechrecognition.forms.LoginForm;

@Controller
public class IndexController {

	
	@RequestMapping("/")
	public String index()
	{
		return "index";
	}
	
	@RequestMapping("/login")
	public String getLogin(LoginForm loginForm)
	{
		return "loginPage";
	}
	
	@PostMapping("/processLogin")
	public String processLogin(@Valid LoginForm loginForm, BindingResult bindingResult)
	{
		return "logged";
	}
	
	@PostMapping("/logout")
	public String logout(@Valid LoginForm loginForm, BindingResult bindingResult)
	{
		return "index";
	}
}
