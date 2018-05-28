package pl.speechrecognition.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private Long userID;
	private String username;
	private String password;

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public User() {
	}

	
	public User(String username) {
		this.username = username;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(Long userID, String username, String password, List<Event> events) {
		this.userID = userID;
		this.username = username;
		this.password = password;
	}

	public String toString() {
		return "Username: " + username;
	}
}
