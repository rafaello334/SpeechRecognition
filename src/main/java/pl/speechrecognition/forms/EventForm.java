package pl.speechrecognition.forms;

import javax.validation.constraints.NotNull;

public class EventForm {

	@NotNull
	private String date;

	@NotNull
	private String time;

	@NotNull
	private String message;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
