package pl.speechrecognition.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.speechrecognition.forms.EventForm;
import pl.speechrecognition.model.Day;
import pl.speechrecognition.model.Event;
import pl.speechrecognition.services.CloudService;
import pl.speechrecognition.services.SimplyTools;

@Controller
public class HomeController {

	@Autowired
	private CloudService cloudService;
	@Autowired
	private SimplyTools simplyTools;

	@GetMapping("/home")
	public ModelAndView getHome(EventForm eventForm, Principal principal, @RequestParam(name = "date", required = false) String date,
			@RequestParam(name = "previousOrNext", required = false, defaultValue="none") String previousOrNext) {
		ModelAndView model = new ModelAndView("home");
		List<Day> dayList = new ArrayList<>();
		List<Event> eventsList = null;
		int missDivs;
		int daysInMonth;
		int year;
		int month;
		int missTemp;
		Calendar calendar;
		
		switch (previousOrNext) {
		case "previousMonth":
			year = Integer.parseInt(date.split("-")[0]);
			month = Integer.parseInt(date.split("-")[1]);
			if (month == 1)
				calendar = new GregorianCalendar(year - 1, 11, 1);
			else
				calendar = new GregorianCalendar(year, month - 2, 1);

			break;
			
		case "nextMonth":
			year = Integer.parseInt(date.split("-")[0]);
			month = Integer.parseInt(date.split("-")[1]);

			if (month == 12)
				calendar = new GregorianCalendar(year + 1, 0, 1);
			else
				calendar = new GregorianCalendar(year, month, 1);
			
			break;
			
		case "previousYear":
			year = Integer.parseInt(date.split("-")[0]);
			month = Integer.parseInt(date.split("-")[1]);

			calendar = new GregorianCalendar(year - 1, month - 1, 1);
			missTemp = calendar.get(Calendar.DAY_OF_WEEK);
			
			break;
			
		case "nextYear":
			year = Integer.parseInt(date.split("-")[0]);
			month = Integer.parseInt(date.split("-")[1]);
			calendar = new GregorianCalendar(year + 1, month - 1, 1);
			
			break;
		default:
			calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			
			break;
		}
		
		missTemp = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(missTemp == 1)
			missDivs = 6;
		else
			missDivs = missTemp - 2;
		
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		eventsList = cloudService.sendRequestGetUserEvents(principal.getName());
		
		if(eventsList == null)
		{	
			eventsList = new ArrayList<>();
		}
		
		for (int i = 0; i < daysInMonth; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, i + 1);
			dayList.add(new Day(calendar.getTime(), simplyTools.findEventsForDay(eventsList, i + 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))));
		}
		model.addObject("username", principal.getName());
		model.addObject("dayList", dayList);
		model.addObject("missDivs", missDivs);
		model.addObject("date", calendar.getTime());
		return model;
	}
}
