package pl.matidominati.GitHubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class RepositoryResponseDto {
    private final String fullName;
    private final String description;
    private final String cloneUrl;
    private final int stars;
    private final LocalDateTime createdAt;
}

