package pl.matidominati.GitHubApp.model.entity.compositeKey;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class RepositoryId implements Serializable {
    private String name;
    private String ownerUsername;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryId that = (RepositoryId) o;
        return Objects.equals(name, that.name) && Objects.equals(ownerUsername, that.ownerUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ownerUsername);
    }
}
