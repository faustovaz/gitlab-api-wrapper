package api.gitlab.wrapper.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class GitlabSnippetTests {

	@Test
	public void fileTypeShouldBeBlankIfFileNameNotSpecified() {
		GitlabSnippet snippet = new GitlabSnippet(1);
		assertEquals("", snippet.fileType());
	}
	
	@Test	
	public void fileTypeShoudBeJavaType() {
		GitlabSnippet snippet = new GitlabSnippet("Java file", "MyClass.java", "Java Code");
		assertEquals("java", snippet.fileType());
	}

	@Test
	public void fileTypeShoudBeXml() {
		GitlabSnippet snippet = new GitlabSnippet("Xml file", "MyXml.file.xml", "xml content");
		assertEquals("xml", snippet.fileType());
	}
	
	@Test
	public void fileTypeShouldBeBlankIfFileNameExtensionNotDefined() {
		GitlabSnippet snippet = new GitlabSnippet("My Dockerfile", "Dockerfile", "Docker instructions");
		assertEquals("", snippet.fileType());
	}
	
	@Test
	public void fileTypeShoudBeReturnedIfFileIsHidden() {
		GitlabSnippet snippet = new GitlabSnippet("Hidden file", ".git", "git file");
		assertEquals("git", snippet.fileType());
	}
	
	@Test
	public void fileTypeShouldBeBlankIfNonConventionalFileName() {
		GitlabSnippet snippet = new GitlabSnippet("Strange file", "file...", "file content");
		assertEquals("", snippet.fileType());
	}
	
	@Test
	public void fileTypeShouldBeBlankIfNoFileName() {
		GitlabSnippet snippet = new GitlabSnippet("Strange file", "", "file content");
		assertEquals("", snippet.fileType());
	}
}
