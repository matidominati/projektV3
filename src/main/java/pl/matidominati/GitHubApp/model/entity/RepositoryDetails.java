package pl.matidominati.GitHubApp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.matidominati.GitHubApp.model.entity.compositeKey.RepositoryId;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@IdClass(RepositoryId.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryDetails {
    @NotNull
    @NotEmpty
    @Id
    private String repositoryName;
    @Id
    private String ownerUsername;
    private String description;
    private String cloneUrl;
    private int stars;
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryDetails that = (RepositoryDetails) o;
        return repositoryName != null && ownerUsername != null
                && Objects.equals(repositoryName, that.repositoryName)
                && Objects.equals(ownerUsername,that.ownerUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repositoryName, ownerUsername);
    }
}
