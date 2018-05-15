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
import pl.speechrecognition.data.UserRepository;
import pl.speechrecognition.forms.EventForm;
import pl.speechrecognition.model.Day;
import pl.speechrecognition.model.Event;
import pl.speechrecognition.model.User;
import pl.speechrecognition.tools.ListSearcher;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private ListSearcher listSearcher;
	List<Event> eventsList;

	@GetMapping("/home")
	public ModelAndView getHome(EventForm eventForm, Principal principal, @RequestParam(name = "date", required = false) String date,
			@RequestParam(name = "previousOrNext", required = false, defaultValue="none") String previousOrNext) {
		ModelAndView model = new ModelAndView("home");
		List<Day> dayList = new ArrayList<>();
		int missDivs;
		int daysInMonth;
		int year;
		int month;
		int missTemp;
		Calendar calendar;
		
		switch (previousOrNext) {
		case "previous":
			year = Integer.parseInt(date.split("-")[0]);
			month = Integer.parseInt(date.split("-")[1]);
			if (month == 1)
				calendar = new GregorianCalendar(year - 1, 11, 1);
			else
				calendar = new GregorianCalendar(year, month - 2, 1);
			missTemp = calendar.get(Calendar.DAY_OF_WEEK);
			
			if(missTemp == 1)
				missDivs = 6;
			else
				missDivs = missTemp - 2;
			
			daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			break;
			
		case "next":
			year = Integer.parseInt(date.split("-")[0]);
			month = Integer.parseInt(date.split("-")[1]);

			if (month == 12)
				calendar = new GregorianCalendar(year + 1, 0, 1);
			else
				calendar = new GregorianCalendar(year, month, 1);
			
			missTemp = calendar.get(Calendar.DAY_OF_WEEK);
			if(missTemp == 1)
				missDivs = 6;
			else
				missDivs = missTemp - 2;
			
			daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			break;
		default:
			calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			
			missTemp = calendar.get(Calendar.DAY_OF_WEEK);
			if(missTemp == 1)
				missDivs = 6;
			else
				missDivs = missTemp - 2;
			
			daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			break;
		}

		eventsList = getListOfEvents(calendar, userRepository.findByUsername(principal.getName()));
		
		for (int i = 0; i < daysInMonth; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, i + 1);
			dayList.add(new Day(calendar.getTime(), listSearcher.findEventsForDay(eventsList, i + 1)));
		}
		
		model.addObject("username", principal.getName());
		model.addObject("dayList", dayList);
		model.addObject("missDivs", missDivs);
		model.addObject("date", calendar.getTime());
		return model;
	}

	private List<Event> getListOfEvents(Calendar calendar, User user) {
		Date from = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date to = calendar.getTime();
		return eventRepository.findByDateBetweenAndUserOrderByDate(from, to, user);
	}
}
