package pl.speechrecognition.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Event {

	@Id
	@GeneratedValue
	private Long eventID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String message;

	@ManyToOne
	@JoinColumn(name = "user_id")
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

	@Override
	public String toString() {
		if(user != null)
			return "USER: " + user.toString() + "   EventID:  " + eventID + "  -  " + message + "   -   "+ date.toString();
		else
			return "EventID  " + eventID + "  -  " + message + "   -   "+ date.toString();
	}
}
