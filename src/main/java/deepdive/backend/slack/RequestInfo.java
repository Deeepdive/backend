package deepdive.backend.slack;

public record RequestInfo(
	String requestURL,
	String method,
	String remoteAddress
) {

}
