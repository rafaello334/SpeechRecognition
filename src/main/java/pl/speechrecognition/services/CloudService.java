package pl.speechrecognition.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pl.speechrecognition.SpeechRecognitionApplication;
import pl.speechrecognition.model.CloudServer;
import pl.speechrecognition.model.Event;
import pl.speechrecognition.model.User;

@Service
public class CloudService {

	private List<CloudServer> cloudServersList;

	@Autowired
	private RestTemplate restTemplate;

	private static final String CLOUD_SERVER_1_NAME = "Server1";
	private static final String CLOUD_SERVER_1_URI = "http://localhost:8081";
	private static final String CLOUD_SERVER_2_NAME = "Server2";
	private static final String CLOUD_SERVER_2_URI = "http://localhost:8082";

	public CloudService() {
		cloudServersList = new ArrayList<>();
		cloudServersList.add(new CloudServer(CLOUD_SERVER_1_NAME, CLOUD_SERVER_1_URI));
		cloudServersList.add(new CloudServer(CLOUD_SERVER_2_NAME, CLOUD_SERVER_2_URI));
	}

	public CloudServer getAvailableServer() {
		for (CloudServer cloudServer : cloudServersList) {
			if (cloudServer.isStatus())
				changeServerStatusToUnAvailable(cloudServer.getServerName());
			return cloudServer;
		}
		return null;
	}

	private void changeServerStatusToUnAvailable(String serverName) {
		cloudServersList.stream().filter(cloud -> cloud.getServerName().equals(serverName)).findFirst().get()
				.setStatus(false);
	}

	private void changeServerStatusToAvailable(String serverName) {
		cloudServersList.stream().filter(cloud -> cloud.getServerName().equals(serverName)).findFirst().get()
				.setStatus(true);
	}

	/*
	 * Configuration requests for Users
	 * 
	 */
	public boolean sendRequestIfUserExists(String username) {
		CloudServer cloudServer = getAvailableServer();
		if (cloudServer != null) {
			User user = restTemplate.getForObject(cloudServer.getURI() + "/users/" + username, User.class);
			changeServerStatusToAvailable(cloudServer.getServerName());
			if (user == null) {
				SpeechRecognitionApplication.logger.info("User: " + username + "does not exist");
				return false;
			}
		}
		return true;
	}

	public void sendRequestSaveUser(User user) {
		CloudServer cloudServer = getAvailableServer();
		if (cloudServer != null) {
			URI uri = restTemplate.postForLocation(cloudServer.getURI() + "/users/", user, User.class);
			changeServerStatusToAvailable(cloudServer.getServerName());
			SpeechRecognitionApplication.logger.info("User created : " + uri.toASCIIString());
		}
	}

	/*
	 * Configuration requests for Events
	 * 
	 */
	public List<Event> sendRequestGetUserEvents(String username) {
		CloudServer cloudServer = getAvailableServer();
		if (cloudServer != null) {
			ResponseEntity<List<Event>> eventsParametrized = restTemplate.exchange(
					cloudServer.getURI() + "/events/" + username, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Event>>() {
					});
			changeServerStatusToAvailable(cloudServer.getServerName());

			List<Event> eventsList = eventsParametrized.getBody();
			return eventsList;
		}
		return null;
	}

	public void sendRequestSaveEvent(Event event) {
		CloudServer cloudServer = getAvailableServer();
		if (cloudServer != null) {
			URI uri = restTemplate.postForLocation(cloudServer.getURI() + "/events/", event, Event.class);
			SpeechRecognitionApplication.logger.info("Event created : " + uri.toASCIIString());
			changeServerStatusToAvailable(cloudServer.getServerName());
		}
	}
	
	/*
	 * Configuration requests for recognizing
	 * 
	 */
	public String sendRecognizeRequest(String fileName) {
		CloudServer cloudServer = getAvailableServer();
		if (cloudServer != null) {
			String recognizedCommand = restTemplate.getForObject(cloudServer.getURI() + "/recognize/" + fileName, String.class);
			SpeechRecognitionApplication.logger.info("Recognized command: " + recognizedCommand);
			changeServerStatusToAvailable(cloudServer.getServerName());
			return recognizedCommand;
		}
		return null;
	}
}
