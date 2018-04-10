package api.gitlab.wrapper.endpoint;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import api.gitlab.wrapper.client.GitlabApiClient;
import api.gitlab.wrapper.model.GitlabSnippet;


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
		JsonArray jsonArray = apiClient().getAll(SNIPPETS_ENDPOINT);
		GitlabSnippet[] allSnippets = gson().fromJson(jsonArray, GitlabSnippet[].class);
		List<GitlabSnippet> snippets = Arrays.asList(allSnippets);
		snippets.forEach(snippet -> this.loadContent(snippet));
		return snippets;	
	}
	
	public GitlabSnippet snippet(GitlabSnippet snippet){
		JsonElement json = apiClient().get(SNIPPETS_ENDPOINT, snippet.id);
		GitlabSnippet loadedSnippet  = gson().fromJson(json, GitlabSnippet.class);
		return loadContent(loadedSnippet);
	}
	
	public GitlabSnippet create(GitlabSnippet snippet) {
		JsonElement json = apiClient().post(SNIPPETS_ENDPOINT, this.gson.toJsonTree(snippet));
		return gson.fromJson(json, GitlabSnippet.class);
	}
	
	public GitlabSnippet remove(GitlabSnippet snippet){
		apiClient().delete(SNIPPETS_ENDPOINT, snippet.id);
		return snippet;
	}
	
	public GitlabApiClient apiClient() {
		return this.apiClient;
	}
	
	public GitlabSnippet loadContent(GitlabSnippet snippet) {
		if(snippet != null && snippet.raw_url != null) {
			String content = apiClient.getRawContent(snippet.raw_url);
			snippet.content = content;
		}
		return snippet;
	}
	
	protected Gson gson() {
		if (this.gson == null)
			this.gson = new Gson();
		return gson;
	}

}
