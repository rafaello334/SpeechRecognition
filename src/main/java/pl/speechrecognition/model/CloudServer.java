package pl.speechrecognition.model;

public class CloudServer {
	private String serverName;
	private boolean status;
	private final String URI;

	public CloudServer(String serverName, String URI) {
		this.serverName = serverName;
		this.URI = URI;
		this.status = true;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getURI() {
		return URI;
	}

}
