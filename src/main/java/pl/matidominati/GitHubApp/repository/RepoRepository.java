package pl.matidominati.GitHubApp.repository;

import pl.matidominati.GitHubApp.model.entity.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoRepository extends JpaRepository<Repo, Long> {
    Optional<Repo> findByRepoNameAndOwner(String repositoryName, String owner);
}
