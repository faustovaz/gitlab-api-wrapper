package api.gitlab.wrapper.endpoint;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import api.gitlab.wrapper.client.GitlabApiClient;
import api.gitlab.wrapper.model.GitlabSnippet;
import okhttp3.Response;


public class GitlabSnippetsApi {

	private GitlabApiClient apiClient;
	private Gson gson;
	
	public static final String SNIPPETS_ENDPOINT = "snippets";
	
	public GitlabSnippetsApi() {}
	
	public GitlabSnippetsApi(String gitlabUrl, String accessToken) {
		this.apiClient = new GitlabApiClient(gitlabUrl, accessToken);
		this.gson = new Gson();
	}
	
	public List<GitlabSnippet> allSnippets(){
		Optional<JsonArray> jsonArray = apiClient().getAll(SNIPPETS_ENDPOINT);
		GitlabSnippet[] allSnippets = gson().fromJson(
				jsonArray.orElse(new JsonArray()), 
				GitlabSnippet[].class);
		List<GitlabSnippet> snippets = Arrays.asList(allSnippets);
		snippets.forEach(snippet -> this.loadContent(snippet));
		return snippets;	
	}
	
	public GitlabSnippet snippet(GitlabSnippet snippet){
		Optional<JsonElement> json = apiClient().get(SNIPPETS_ENDPOINT, snippet.id);
		if (json.isPresent()) {
			GitlabSnippet loadedSnippet  = gson().fromJson(json.get(), GitlabSnippet.class);
			return loadContent(loadedSnippet);
		}
		return new GitlabSnippet();
	}
	
	public Boolean create(GitlabSnippet snippet) throws IOException {
		Response response = apiClient().post(SNIPPETS_ENDPOINT, this.gson.toJsonTree(snippet));
		return response.isSuccessful();
	}
	
	public Boolean remove(GitlabSnippet snippet) throws IOException {
		Response response = apiClient().delete(SNIPPETS_ENDPOINT, snippet.id);
		return response.isSuccessful();
	}
	
	public GitlabApiClient apiClient() {
		return this.apiClient;
	}
	
	public GitlabSnippet loadContent(GitlabSnippet snippet) {
		if(snippet != null && snippet.raw_url != null) {
			String content = apiClient.getContent(snippet.raw_url);
			snippet.raw_url = content;
		}
		return snippet;
	}
	
	protected Gson gson() {
		if (this.gson == null)
			this.gson = new Gson();
		return gson;
	}

}
