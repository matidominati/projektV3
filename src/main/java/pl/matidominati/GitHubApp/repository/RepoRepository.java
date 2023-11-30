package pl.matidominati.GitHubApp.repository;

import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoRepository extends JpaRepository<RepositoryDetails, Long> {
    Optional<RepositoryDetails> findByOwnerUsernameAndRepositoryName(String ownerUsername, String repositoryName);
}
