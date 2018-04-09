package api.gitlab.wrapper.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import api.gitlab.wrapper.GitlabAPI;
import api.gitlab.wrapper.client.GitlabApiClient;
import api.gitlab.wrapper.exception.GitlabApiException;
import api.gitlab.wrapper.model.GitlabSnippet;

public class GitlabSnippetsApiTests {
	
	GitlabApiClient client;
	GitlabSnippetsApi snippetsApi;
	JsonParser parser;
	
	@Before
	public void setup() {
		client = mock(GitlabApiClient.class);
		snippetsApi = spy(GitlabSnippetsApi.class);
		when(snippetsApi.apiClient()).thenReturn(client);
		parser = new JsonParser();
	}
	
	@Test
	public void shouldReturnAnEmptyList() {
		JsonArray array = parser.parse("[]").getAsJsonArray();
		when(client.getAll(GitlabSnippetsApi.SNIPPETS_ENDPOINT)).thenReturn(array);
		List<GitlabSnippet> allSnippets = snippetsApi.allSnippets();
		assertTrue(allSnippets.isEmpty());
	}

	@Test
	public void shouldReturnSpecificSnippet() {
		JsonElement json = parser.parse("{'id': 1, 'title': 'title', 'file_name': 'file.java'}");
		when(client.get(GitlabSnippetsApi.SNIPPETS_ENDPOINT, 1)).thenReturn(json);
		GitlabSnippet snippet = snippetsApi.snippet(new GitlabSnippet(1));
		assertEquals(snippet.id, new Integer(1));
		assertEquals(snippet.title, "title");
		assertEquals(snippet.file_name, "file.java");
	}
	
	@Test
	public void shouldReturnAListOfSnippets() {
		JsonArray jsonList = parser.parse(
				"[{'id': 1, 'title': 'title', 'file_name': 'file.java'},"
				+ "{'id': 2, 'title': 'title2', 'file_name': 'file2.java'},"
				+ "{'id': 3, 'title': 'title3', 'file_name': 'file3.java'}]"
		).getAsJsonArray();
		when(client.getAll(GitlabSnippetsApi.SNIPPETS_ENDPOINT)).thenReturn(jsonList);
		List<GitlabSnippet> allSnippets = snippetsApi.allSnippets();
		assertEquals(allSnippets.size(), 3);
	}
	
	@Test(expected = GitlabApiException.class)
	public void shouldThrowGitlabApiExceptionIfBadResponse() {
		GitlabAPI api = new GitlabAPI("https://fake-url.org", "fake-token");
		List<GitlabSnippet> snippets = api.snippets().allSnippets();
		assertTrue(snippets.isEmpty());
	}
	
}
