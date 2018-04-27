package pl.speechrecognition.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {

	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9]+")
	@Size(min = 3, max = 20)
	private String username;

	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9!@#$%^&*=+.]+")
	@Size(min = 3, max = 20)
	private String password;

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
	
	
}
