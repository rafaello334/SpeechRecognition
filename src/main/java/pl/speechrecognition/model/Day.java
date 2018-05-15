package pl.speechrecognition.model;

import java.util.Date;
import java.util.List;

public class Day {

	private Date date;
	private List<Event> eventsList;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Event> getEventsList() {
		return eventsList;
	}
	public void setEventsList(List<Event> eventsList) {
		this.eventsList = eventsList;
	}
	public Day(Date date, List<Event> eventsList) {
		this.date = date;
		this.eventsList = eventsList;
	}
	
	public String toString()
	{
		StringBuilder output = new StringBuilder();
		
		for(Event event : eventsList)
		{
			output.append(event.getDate().toString() + "  ");
			output.append(event.getMessage());
		}
		
		
		return output.toString();
	}
	
}
