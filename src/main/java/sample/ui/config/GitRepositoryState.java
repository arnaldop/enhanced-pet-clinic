package sample.ui.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

public class GitRepositoryState extends pl.project13.maven.git.GitRepositoryState {

	public GitRepositoryState(Properties properties) {
		super();

		setTags(new HashSet<String>(Arrays.asList(properties.get("git.tags").toString().split(","))));
		setBranch(properties.get("git.branch").toString());

		setCommitId(properties.get("git.commit.id").toString());
		setCommitIdAbbrev(properties.get("git.commit.id.abbrev").toString());
		setCommitUserName(properties.get("git.commit.user.name").toString());
		setCommitUserEmail(properties.get("git.commit.user.email").toString());
		setCommitMessageFull(properties.get("git.commit.message.full").toString());
		setCommitMessageShort(properties.get("git.commit.message.short").toString());
		setCommitTime(properties.get("git.commit.time").toString());

		setBuildUserName(properties.get("git.build.user.name").toString());
		setBuildUserEmail(properties.get("git.build.user.email").toString());
		setBuildTime(properties.get("git.build.time").toString());
	}
}
