package pl.speechrecognition.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

public class LoginForm {

	@Getter
	@Setter
	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9]+")
	@Size(min = 3, max = 20)
	private String username;

	@Getter
	@Setter
	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9!@#$%^&*=+.]+")
	@Size(min = 3, max = 20)
	private String password;
}
