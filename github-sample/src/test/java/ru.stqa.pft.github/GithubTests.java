package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("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");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("GromovJR", "JavaMemories")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
