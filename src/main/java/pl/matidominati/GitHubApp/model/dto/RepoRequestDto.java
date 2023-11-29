package pl.matidominati.GitHubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.matidominati.GitHubApp.model.entity.Owner;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RepoRequestDto {
    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private LocalDateTime createdAt;
    private Owner owner;
}
