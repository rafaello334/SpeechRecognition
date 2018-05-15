package pl.speechrecognition.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import pl.speechrecognition.SpeechRecognitionApplication;
import pl.speechrecognition.data.EventRepository;
import pl.speechrecognition.data.UserRepository;
import pl.speechrecognition.forms.EventForm;
import pl.speechrecognition.model.Event;

@Controller
public class EventsController {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("addEvent")
	public String addEvent(Principal principal, @Valid EventForm eventForm, BindingResult bindingResult) {
		try {
			String date = eventForm.getDate() + " " + eventForm.getTime();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
			Event event = new Event();
			event.setDate(format.parse(date));
			event.setMessage(eventForm.getMessage());
			event.setUser(userRepository.findByUsername(principal.getName()));
			eventRepository.save(event);
			SpeechRecognitionApplication.logger.info("Event created: " + event);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "redirect:/home";
	}
}
