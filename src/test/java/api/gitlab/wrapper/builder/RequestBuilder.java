package api.gitlab.wrapper.builder;

import okhttp3.Request;

public class RequestBuilder {

	Request.Builder builder;
	
	public RequestBuilder withUrl(String url) {
		builder.url(url);
		return this;
	}
	
	public Request build() {
		return builder.build();
	}
}
