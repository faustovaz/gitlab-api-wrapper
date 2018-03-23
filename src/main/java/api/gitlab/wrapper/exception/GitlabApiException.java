package api.gitlab.wrapper.exception;

public class GitlabApiException extends RuntimeException {

	private static final long serialVersionUID = 4625402201102972436L;

	public GitlabApiException(String message) {
		super(message);
	}
	
	public GitlabApiException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
