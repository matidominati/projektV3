package pl.matidominati.GitHubApp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.matidominati.GitHubApp.model.entity.compositeKey.RepositoryId;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@IdClass(RepositoryId.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryDetails {
    @Id
    private String name;
    @Id
    private String ownerUsername;
    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private LocalDateTime createdAt;
}
