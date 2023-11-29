package pl.matidominati.GitHubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.matidominati.GitHubApp.model.entity.Owner;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RepoRequestDto {
    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final int stars;
    private final LocalDateTime date;
    private final Owner owner;
}
