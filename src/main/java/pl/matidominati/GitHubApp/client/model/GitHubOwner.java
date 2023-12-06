package pl.matidominati.GitHubApp.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GitHubOwner {
    private final Long id;
    private final String login;
}
