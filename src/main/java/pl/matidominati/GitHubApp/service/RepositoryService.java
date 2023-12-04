package pl.matidominati.GitHubApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matidominati.GitHubApp.exception.DataAlreadyExistsException;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.mapper.RepositoryMapper;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
import pl.matidominati.GitHubApp.repository.LocalRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    private final RepositoryMapper mapper;
    private final LocalRepository repository;
    private final GitHubService clientService;

    @Transactional
    public RepositoryResponseDto saveRepository(String owner, String repositoryName) {
        var repositoryPojo = clientService.convertClientRepositoryToPojo(owner, repositoryName);
        repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)
                .ifPresent(existingRepository -> {
                    throw new DataAlreadyExistsException("This resource is in the database");
                });
        var localRepository = createRepository(repositoryPojo);
        repository.save(localRepository);
        return mapper.mapRepositoryDetailsToResponseDto(localRepository);
    }

    public RepositoryResponseDto getRepository(String owner, String repositoryName) {
        var localRepository = repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        return mapper.mapRepositoryDetailsToResponseDto(localRepository);
    }

    @Transactional
    public RepositoryResponseDto editRepository(String owner, String repositoryName, RepositoryPojo updatedRepository) {
        var originalRepository = getDetails(owner, repositoryName);
        updateRepositoryDetails(originalRepository, updatedRepository);
        var repoToSave = mapper.mapPojoToRepositoryDetails(originalRepository);
        repository.save(repoToSave);
        return mapper.mapRepositoryDetailsToResponseDto(repoToSave);
    }

    @Transactional
    public void deleteRepository(String owner, String repositoryName) {
        RepositoryDetails repositoryToDelete = repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        repository.delete(repositoryToDelete);
    }

    public RepositoryPojo getDetails(String owner, String repositoryName) {
        var localRepository = repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        return mapper.mapRepositoryDetailsToPojo(localRepository);
    }

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
