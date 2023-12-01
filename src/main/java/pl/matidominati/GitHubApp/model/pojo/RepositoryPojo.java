package pl.matidominati.GitHubApp.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
public class RepositoryPojo {
    private String fullName;
    private String name;
    private String cloneUrl;
    private String description;
    private String ownerUsername;
    private int stars;
    private LocalDateTime createdAt;
}