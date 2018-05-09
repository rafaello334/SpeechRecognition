package pl.speechrecognition.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.speechrecognition.data.EventRepository;
import pl.speechrecognition.model.Event;

@Controller
public class HomeController {

	@Autowired
	private EventRepository eventRepository;

	List<Event> eventsList;

	@GetMapping("/home")
	public ModelAndView getHome(Principal principal) {
		ModelAndView model = new ModelAndView("home");
		List<String> dayList = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int missDivs = calendar.get(Calendar.DAY_OF_WEEK) - 2;
		eventsList = getListOfEvents(calendar);
		int daysInMonth = calendar.get(Calendar.DAY_OF_MONTH);

		for (int i = 0; i < daysInMonth; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, i + 1);
			dayList.add("" + calendar.get(Calendar.DAY_OF_MONTH));
		}	

		model.addObject("username", principal.getName());
		model.addObject("eventsList", eventsList);
		model.addObject("dayList", dayList);
		model.addObject("missDivs", missDivs);
		model.addObject("date", calendar.getTime());
		return model;
	}
	
	@GetMapping("/previousMonth")
	public ModelAndView getPreviousMonth(Principal principal, @RequestParam("date") String date) {
		ModelAndView model = new ModelAndView("home");
		List<String> dayList = new ArrayList<>();
		Calendar calendar;
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		
		if(month == 1)
		  calendar = new GregorianCalendar(year - 1, 11, 1);		
		else
		  calendar = new GregorianCalendar(year, month - 2, 1);		
		int missDivs = calendar.get(Calendar.DAY_OF_WEEK) - 2;
		eventsList = getListOfEvents(calendar);
		int daysInMonth = calendar.get(Calendar.DAY_OF_MONTH);

		for (int i = 0; i < daysInMonth; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, i + 1);
			dayList.add("" + calendar.get(Calendar.DAY_OF_MONTH));
		}	
		
		model.addObject("username", principal.getName());
		model.addObject("eventsList", eventsList);
		model.addObject("dayList", dayList);
		model.addObject("missDivs", missDivs);
		model.addObject("date", calendar.getTime());
		return model;
	}
	
	@GetMapping("/nextMonth")
	public ModelAndView getNextMonth(Principal principal, @RequestParam("date") String date) {
		ModelAndView model = new ModelAndView("home");
		List<String> dayList = new ArrayList<>();
		Calendar calendar;
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		
		if(month == 12)
			calendar = new GregorianCalendar(year + 1, 0, 1);		
		else
			calendar = new GregorianCalendar(year, month, 1);			
		int missDivs = calendar.get(Calendar.DAY_OF_WEEK) - 2;
		eventsList = getListOfEvents(calendar);
		int daysInMonth = calendar.get(Calendar.DAY_OF_MONTH);

		for (int i = 0; i < daysInMonth; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, i + 1);
			dayList.add("" + calendar.get(Calendar.DAY_OF_MONTH));
		}	
		
		model.addObject("username", principal.getName());
		model.addObject("eventsList", eventsList);
		model.addObject("dayList", dayList);
		model.addObject("missDivs", missDivs);
		model.addObject("date", calendar.getTime());
		return model;
	}

	private List<Event> getListOfEvents(Calendar calendar)
	{
		Date from = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date to = calendar.getTime();
		return eventRepository.findByEventDateBetween(from, to);
	}
}
