package api.gitlab.wrapper.client;

import java.io.IOException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import api.gitlab.wrapper.exception.GitlabApiException;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GitlabApiClient{

	private String gitlabUrl;
	private String accessToken;
	private OkHttpClient http;
	private JsonParser parser;
	
	public static final String API_ENDPOINT = "/api/v4";
	
	public GitlabApiClient() {
		this.http = new OkHttpClient();
		this.parser = new JsonParser();
	}

	public GitlabApiClient(String gitlabUrl, String accessToken) {
		this();
		this.gitlabUrl = gitlabUrl;
		this.accessToken = accessToken;
	}
	
	public String getRawContent(String url) {
		try {
			Response response = call(new URL(url));
			checkResponse(response);
			return response.body().string();			
		}
		catch(Exception e) {
			throw new GitlabApiException(e.getMessage(), e.getCause());
		}
	}
	
	public JsonElement get(String endpoint, Integer id){
		try {
			Response response = call(endpoint,id);
			checkResponse(response);
			return parser.parse(response.body().string());
		}
		catch(Exception e) {
			throw new GitlabApiException(e.getMessage(), e.getCause());
		}
	}
	
	public JsonArray getAll(String endpoint){
		try {
			Response response = call(endpoint);
			checkResponse(response);
			return parser.parse(response.body().string()).getAsJsonArray();
		}
		catch(Exception e) {
			throw new GitlabApiException(e.getMessage(), e.getCause());
		}
	}
	
	public JsonElement put(String endpoint, Integer id ,JsonElement json){
		try {
			Request request = buildRequest(endpoint, id)
				.put(RequestBody.create(
						MediaType.parse("application/json"), 
						json.getAsString()))
				.build();
			Response response = http.newCall(request).execute();
			checkResponse(response);
			return parser.parse(response.body().string());				
		}
		catch(Exception e) {
			throw new GitlabApiException(e.getMessage(), e.getCause());
		}
	}
	
	public void delete(String endpoint, Integer id){
		try {
			Request request = buildRequest(endpoint, id)
							.delete()
							.build();
			Response response = http.newCall(request).execute();
			checkResponse(response);
		}
		catch(Exception e) {
			throw new GitlabApiException(e.getMessage(), e.getCause());
		}
	}
	
	public JsonElement post(String endpoint, JsonElement json){
		try {
			Request request = buildRequest(endpoint)
				.post(RequestBody.create(
							MediaType.parse("application/json"), 
							json.getAsString()))
				.build();
			Response response = http.newCall(request).execute();
			checkResponse(response);
			return parser.parse(response.body().string());			
		}
		catch(Exception e) {
			throw new GitlabApiException(e.getMessage(), e.getCause());
		}
	}
	
	public void checkResponse(Response response) throws Exception {
		if(response != null && !response.isSuccessful()) {
			Request request = response.request();
			Integer httpCode = response.code();
			response.close();
			throw new Exception("Bad response. Request to " 
					+ request.url().toString()
					+ " returned HTTP code " 
					+ httpCode);
		}
	}
	
	public Response call(URL url) throws IOException {
		Request request = new Request.Builder()
					.addHeader("PRIVATE-TOKEN", this.accessToken)
					.url(url)
					.build();
		Call call = http.newCall(request);
		return call.execute();
	}
	
	public Response call(String endpoint) throws IOException {
		Call call = http.newCall(buildRequest(endpoint).build());
		return call.execute();
	}
	
	public Response call(String endpoint, Integer id) throws IOException {
		Call call = http.newCall(buildRequest(endpoint, id).build());
		return call.execute();
	}
	
	protected HttpUrl buildUrl(String endpoint, Integer id) {
		HttpUrl url = buildUrl(endpoint);
		return url.newBuilder()
				.addPathSegment(String.valueOf(id))
				.build();
	}
	
	protected HttpUrl buildUrl(String endpoint) {
		return HttpUrl.parse(this.gitlabUrl)
				.newBuilder(API_ENDPOINT)
				.addPathSegments(endpoint)
				.build();
	}
	
	protected Request.Builder buildRequest(String endpoint){
		return new Request.Builder()
					.url(buildUrl(endpoint))
					.addHeader("PRIVATE-TOKEN", this.accessToken);
	}
	
	protected Request.Builder buildRequest(String endpoint, Integer id){
		return new Request.Builder()
					.url(buildUrl(endpoint, id))
					.addHeader("PRIVATE-TOKEN", this.accessToken);
	}
	
	public void setGitlabUrl(String url) {
		this.gitlabUrl = url;
	}
	
	public void setAccessToken(String token) {
		this.accessToken = token;
	}
	
	public void setCredentials(String url, String token) {
		setGitlabUrl(url);
		setAccessToken(token);
	}
}
