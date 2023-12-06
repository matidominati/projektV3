package pl.matidominati.GitHubApp.service.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RepositoryFactory {
    public static void updateRepositoryDetails(RepositoryPojo originalRepository, RepositoryPojo updatedRepository) {
        if (updatedRepository.getFullName() != null && !updatedRepository.getFullName().equals(originalRepository.getFullName())) {
            originalRepository.setFullName(updatedRepository.getFullName());
        }
        if (updatedRepository.getDescription() != null && !updatedRepository.getDescription().equals(originalRepository.getDescription())) {
            originalRepository.setDescription(updatedRepository.getDescription());
        }
        if (updatedRepository.getCloneUrl() != null && !updatedRepository.getCloneUrl().equals(originalRepository.getCloneUrl())) {
            originalRepository.setCloneUrl(updatedRepository.getCloneUrl());
        }
        if (updatedRepository.getStars() != originalRepository.getStars()) {
            originalRepository.setStars(updatedRepository.getStars());
        }
        if (updatedRepository.getCreatedAt() != null && !updatedRepository.getCreatedAt().isAfter(LocalDateTime.now())) {
            originalRepository.setCreatedAt(updatedRepository.getCreatedAt());
        }
    }

    public static RepositoryDetails createRepository(RepositoryPojo repositoryPojo) {
        var localRepository = new RepositoryDetails();
        localRepository.setDescription(repositoryPojo.getDescription());
        localRepository.setCreatedAt(repositoryPojo.getCreatedAt());
        localRepository.setStars(repositoryPojo.getStars());
        localRepository.setCloneUrl(repositoryPojo.getCloneUrl());
        localRepository.setFullName(repositoryPojo.getFullName());
        localRepository.setName(repositoryPojo.getName());
        localRepository.setOwnerUsername(repositoryPojo.getOwnerUsername());
        return localRepository;
    }
}
