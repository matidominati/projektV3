package pl.matidominati.GitHubApp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GitHubRepository {
    private final Long id;
    private final String name;
    @JsonProperty("full_name")
    private final String fullName;
    private final String language;
    @JsonProperty("default_branch")
    private final String defaultBranch;
    @JsonProperty("clone_url")
    private final String cloneUrl;
    private final String description;
    private final int watchers;
    @JsonProperty("has_downloads")
    private final boolean hasDownloads;
    private final boolean archived;
    private final boolean disabled;
    private final GitHubOwner gitHubOwner;
    @JsonProperty("stargazers_count")
    private final int stargazersCount;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;

}
