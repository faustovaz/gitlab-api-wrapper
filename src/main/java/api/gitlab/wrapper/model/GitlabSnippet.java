package api.gitlab.wrapper.model;

import java.util.Date;

public class GitlabSnippet {
	public Integer id;
	public String title;
	public String file_name;
	public String description;
	public Author author;
	public Date updated_at;
	public Date created_at;
	public Integer project_id;
	public String web_url;
	public String raw_url;
	public String visibility;
	public String content;
	
	public GitlabSnippet(){}
	
	public GitlabSnippet(Integer id){
		this.id = id;
	}
	
	public GitlabSnippet(String title, String fileName, String content){
		this.title = title;
		this.file_name = fileName;
		this.content = content;
	}
	
	public GitlabSnippet(String title, String fileName, 
							String content, String description, String visibility){
		this(title, fileName, content);
		this.description = description;
		this.visibility = visibility;
	}
	
	public String fileType() {
		if(file_name != null) {
			String[] parts = file_name.split("\\.");
			if (parts.length > 1) {
				String extension = parts[parts.length - 1];
				return extension;
			}
		}
		return "";
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s - %s", title, file_name, description);
	}
	
}

class Author {
	public Integer id;
	public String name;
	public String username;
	public String state;
	public String avatar_url;
	public String web_url;
}