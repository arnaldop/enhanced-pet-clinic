package sample.ui.web;

import java.io.IOException;
import java.util.Properties;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sample.ui.config.GitRepositoryState;

@RestController
public class GitService {

	private GitRepositoryState gitRepositoryState = null;

	public GitRepositoryState getGitRepositoryState() throws IOException {
		if (gitRepositoryState == null) {
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("git.properties"));

			gitRepositoryState = new GitRepositoryState(properties);
		}
		return gitRepositoryState;
	}

	@RequestMapping(value = "/manage/git", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String checkGitRevision() throws IOException {
		return getGitRepositoryState().toJson();
	}
}
