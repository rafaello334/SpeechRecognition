package pl.speechrecognition.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import pl.speechrecognition.forms.EventForm;
import pl.speechrecognition.model.Event;
import pl.speechrecognition.model.User;
import pl.speechrecognition.services.CloudService;

@Controller
public class EventController {

	@Autowired
	private CloudService cloudService;

	@PostMapping("/addEvent")
	public String addEvent(Principal principal, @Valid EventForm eventForm, BindingResult bindingResult) {
		try {
			String date = eventForm.getDate() + " " + eventForm.getTime();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
			Event event = new Event();
			event.setDate(format.parse(date));
			event.setMessage(eventForm.getMessage());
			event.setUser(new User(principal.getName()));
			
	        cloudService.sendRequestSaveEvent(event);
	        

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "redirect:/home";
	}
}
