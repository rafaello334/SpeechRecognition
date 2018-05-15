package pl.speechrecognition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import pl.speechrecognition.tools.ListSearcher;

@Configuration
@ComponentScan("pl.speechrecognition")
public class SpringConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ListSearcher listSearcher() {
	    return new ListSearcher();
	}
}
