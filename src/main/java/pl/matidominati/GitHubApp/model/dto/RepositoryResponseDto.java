package pl.matidominati.GitHubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RepositoryResponseDto {
    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final int stars;
    private final LocalDateTime createdAt;
}

