package pl.matidominati.GitHubApp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalRepository extends JpaRepository<RepositoryDetails, Long> {
        @Query("SELECT rd " +
            "FROM RepositoryDetails rd " +
            "WHERE rd.ownerUsername = :ownerUsername " +
            "AND rd.name = :name")
    Optional<RepositoryDetails> findByOwnerUsernameAndRepositoryName(@Param("ownerUsername") String ownerUsername,@Param("name") String name);
}
