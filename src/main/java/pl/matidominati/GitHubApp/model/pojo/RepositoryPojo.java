package pl.matidominati.GitHubApp.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RepositoryPojo {
    private String fullName;
    private String name;
    private String cloneUrl;
    private String description;
    private String ownerUsername;
    private int stars;
    private LocalDateTime createdAt;
}
