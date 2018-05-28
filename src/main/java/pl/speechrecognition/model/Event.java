package pl.speechrecognition.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {


	private Long eventID;
	private Date date;
	private String message;
	private User user;

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event()
	{
		
	}
	
	public Event(Long eventID, Date date, String message, User user) {
		this.eventID = eventID;
		this.date = date;
		this.message = message;
		this.user = user;
	}
}
