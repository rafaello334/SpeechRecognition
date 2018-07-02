package pl.speechrecognition;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpeechRecognitionApplication {
	public static Log logger = LogFactory.getLog(SpeechRecognitionApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpeechRecognitionApplication.class, args);
	}
}
