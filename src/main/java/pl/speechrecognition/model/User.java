package pl.speechrecognition.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Long userID;
	
	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String password;
}
