package api.gitlab.wrapper.builder;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseBuilder {

	Response.Builder builder;
	
	public ResponseBuilder() {
		this.builder = new Response.Builder();
	}
	
	public ResponseBuilder httpProtocol() {
		this.builder.protocol(Protocol.HTTP_2);
		return this;
	}
	
	public ResponseBuilder withMessage(String message) {
		this.builder.message(message);
		return this;
	}
	
	public ResponseBuilder withCode(Integer code) {
		this.builder.code(code);
		return this;
	}
	
	public ResponseBuilder withResquest(Request request) {
		this.builder.request(request);
		return this;
	}
	
	public ResponseBuilder withBody(String mediaType, String content) {
		this.builder.body(ResponseBody.create(MediaType.parse(mediaType), content));
		return this;
	}
	
	public Response build() {
		return this.builder.build();
	}
	
}
