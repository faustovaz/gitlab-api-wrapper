package api.gitlab.wrapper;

import api.gitlab.wrapper.endpoint.GitlabSnippetsApi;

public class GitlabAPI {
	
	private String gitlabAPIUrl;
	private String accessToken;
	private GitlabSnippetsApi gitlabSnippetsApi;
	
	public GitlabAPI(String gitlabAPIUrl, String accessToken){
		this.gitlabAPIUrl = gitlabAPIUrl;
		this.accessToken = accessToken;
		this.gitlabSnippetsApi = null;
	}
	
	public String gitlabApiUrl() {
		return this.gitlabAPIUrl;
	}
	
	public String accessToken() {
		return this.accessToken;
	}
	
	public GitlabSnippetsApi snippets() {
		if (gitlabSnippetsApi == null)
			gitlabSnippetsApi = new GitlabSnippetsApi(this.gitlabAPIUrl, 
														this.accessToken);
		return gitlabSnippetsApi;
	}
	
}
 