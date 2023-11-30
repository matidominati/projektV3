package pl.matidominati.GitHubApp.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GitHubOwner {
    private final Long id;
    private final String login;
}
