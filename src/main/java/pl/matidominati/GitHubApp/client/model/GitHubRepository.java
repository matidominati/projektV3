package pl.matidominati.GitHubApp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class GitHubRepository {
    private final Long id;
    @JsonProperty("full_name")
    private final String fullName;
    private final String name;
    @JsonProperty("clone_url")
    private final String cloneUrl;
    private final String description;
    @JsonProperty("owner")
    private final GitHubOwner gitHubOwner;
    @JsonProperty("stargazers_count")
    private final int stargazersCount;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
}
