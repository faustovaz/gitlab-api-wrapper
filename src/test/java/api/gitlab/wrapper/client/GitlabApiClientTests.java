package api.gitlab.wrapper.client;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import api.gitlab.wrapper.builder.ResponseBuilder;
import api.gitlab.wrapper.exception.GitlabApiException;
import okhttp3.Request;
import okhttp3.Response;

public class GitlabApiClientTests {

	private GitlabApiClient client;
	private GitlabApiClient mockedClient;
	
	@Test(expected = GitlabApiException.class)
	public void shouldThrowExceptionIfBadHttpResponse() {
		client = new GitlabApiClient("fake-gitlab-url", "fake-accesstoken");
		client.getAll("/snippets");
	}
	
	@Test(expected = GitlabApiException.class)
	public void shouldThrowExceptionIfUnauthorized() throws Exception {
		mockedClient = mock(GitlabApiClient.class);		
		Response fakeResponse = new ResponseBuilder().withCode(404)
					.withMessage("Message")
					.withResquest(new Request.Builder().url("http://fake.gov").build())
					.withBody("application/json", "Unauthorized")
					.httpProtocol()
					.build();
		when(mockedClient.call("/snippets")).thenReturn(fakeResponse);
		doCallRealMethod().when(mockedClient).getAll("/snippets");
		mockedClient.getAll("/snippets");
	}
}

