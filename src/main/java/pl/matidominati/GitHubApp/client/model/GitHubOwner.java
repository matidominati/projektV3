package pl.matidominati.GitHubApp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GitHubOwner {
    private final Long id;
    private final String login;
    private final String type;
    @JsonProperty("html_url")
    private final String htmlUrl;
}
