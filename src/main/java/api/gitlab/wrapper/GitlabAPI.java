package api.gitlab.wrapper;

import java.net.MalformedURLException;

import api.gitlab.wrapper.endpoint.GitlabSnippetsApi;
import okhttp3.HttpUrl;

public class GitlabAPI {
	
	private String gitlabAPIUrl;
	private String accessToken;
	private GitlabSnippetsApi gitlabSnippetsApi;
	
	public GitlabAPI(String gitlabAPIUrl, String accessToken) throws MalformedURLException{
		this.setApiURL(gitlabAPIUrl);
		this.accessToken = accessToken;
		this.gitlabSnippetsApi = null;
	}
	
	public String gitlabApiUrl() {
		return this.gitlabAPIUrl;
	}
	
	public String accessToken() {
		return this.accessToken;
	}
	
	protected void setApiURL(String url) throws MalformedURLException {
		if (HttpUrl.parse(url) == null)
			throw new MalformedURLException(
					String.format("The informed URL <%s> is invalid", url));
		this.gitlabAPIUrl = url;
	}
	
	public GitlabSnippetsApi snippets() {
		if (gitlabSnippetsApi == null)
			gitlabSnippetsApi = new GitlabSnippetsApi(this.gitlabAPIUrl, 
														this.accessToken);
		return gitlabSnippetsApi;
	}

}
 