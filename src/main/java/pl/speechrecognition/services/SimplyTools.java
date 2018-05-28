package pl.speechrecognition.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.speechrecognition.model.Event;

@Component
public class SimplyTools {

	private Calendar calendar = new GregorianCalendar();

	public List<Event> findEventsForDay(List<Event> eventsList, int day) {
		List<Event> dayEvents = new ArrayList<>();
		for (Event event : eventsList) {
			calendar.setTime(event.getDate());
			if (day == calendar.get(Calendar.DAY_OF_MONTH))
				dayEvents.add(event);
		}
		return dayEvents;
	}

}
