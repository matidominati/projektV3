package pl.matidominati.GitHubApp.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matidominati.GitHubApp.client.GitHubClient;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.exception.DataAlreadyExistsException;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.mapper.RepositoryMapper;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
import pl.matidominati.GitHubApp.repository.LocalRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient client;
    private final RepositoryMapper mapper;
    private final LocalRepository repository;

    public RepositoryResponseDto getClientRepositoryDetails(String owner, String repositoryName) {
        RepositoryPojo repositoryPojo = convertClientRepositoryToPojo(owner, repositoryName);
        return mapper.mapPojoToDto(repositoryPojo);
    }

    public List<RepositoryResponseDto> getClientRepositories(String owner) {
        try {
            List<RepositoryPojo> repositoryPojos = client.getRepositories(owner).stream()
                    .map(mapper::mapGitHubRepositoryToPojo)
                    .toList();
            return repositoryPojos.stream()
                    .map(mapper::mapPojoToDto)
                    .toList();
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("User not found");
        }
    }

    @Transactional
    public RepositoryResponseDto saveRepositoryDetails(String owner, String repositoryName) {
        var repositoryPojo = convertClientRepositoryToPojo(owner, repositoryName);
        repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)
                .ifPresent(existingRepository -> {
                    throw new DataAlreadyExistsException("This resource is in the database");
                });
        var localRepository = new RepositoryDetails();
        localRepository.setDescription(repositoryPojo.getDescription());
        localRepository.setCreatedAt(repositoryPojo.getCreatedAt());
        localRepository.setStars(repositoryPojo.getStars());
        localRepository.setCloneUrl(repositoryPojo.getCloneUrl());
        localRepository.setFullName(repositoryPojo.getFullName());
        localRepository.setName(repositoryPojo.getName());
        localRepository.setOwnerUsername(repositoryPojo.getOwnerUsername());
        repository.save(localRepository);
        return mapper.mapRepositoryDetailsToResponseDto(localRepository);
    }

    public RepositoryResponseDto getLocalRepositoryDetails(String owner, String repositoryName) {
        var localRepository = repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        return mapper.mapRepositoryDetailsToResponseDto(localRepository);
    }

    @Transactional
    public RepositoryResponseDto editLocalRepositoryDetails(String owner, String repositoryName, RepositoryPojo repositoryPojo) {
        var repositoryToEdit = getDetails(owner, repositoryName);
        boolean fullNameChanged = repositoryPojo.getFullName() != null && !Objects.equals(repositoryPojo.getFullName(), repositoryToEdit.getFullName());
        boolean descriptionChanged = repositoryPojo.getDescription() != null && !Objects.equals(repositoryPojo.getDescription(), repositoryToEdit.getDescription());
        boolean cloneUrlChanged = repositoryPojo.getCloneUrl() != null && !Objects.equals(repositoryPojo.getCloneUrl(), repositoryToEdit.getCloneUrl());
        boolean starsChanged = repositoryPojo.getStars() != repositoryToEdit.getStars();
        boolean createdAtChanged = repositoryPojo.getCreatedAt() != null && !Objects.equals(repositoryPojo.getCreatedAt(), repositoryToEdit.getCreatedAt()) &&
                !repositoryPojo.getCreatedAt().isAfter(LocalDateTime.now());
        if (fullNameChanged) {
            repositoryToEdit.setFullName(repositoryPojo.getFullName());
        }
        if (descriptionChanged) {
            repositoryToEdit.setDescription(repositoryPojo.getDescription());
        }
        if (cloneUrlChanged) {
            repositoryToEdit.setCloneUrl(repositoryPojo.getCloneUrl());
        }
        if (starsChanged) {
            repositoryToEdit.setStars(repositoryPojo.getStars());
        }
        if (createdAtChanged) {
            repositoryToEdit.setCreatedAt(repositoryPojo.getCreatedAt());
        }
        var repoToSave = mapper.mapPojoToRepositoryDetails(repositoryToEdit);
        repository.save(repoToSave);
        var responseDto = mapper.mapPojoToUpdateRepositoryResponseDto(repositoryPojo);
        responseDto.setFullNameChanged(fullNameChanged);
        responseDto.setDescriptionChanged(descriptionChanged);
        responseDto.setCloneUrlChanged(cloneUrlChanged);
        responseDto.setStarsChanged(starsChanged);
        responseDto.setCreatedAtChanged(createdAtChanged);
        return responseDto;
    }

    @Transactional
    public void deleteLocalRepositoryDetails(String owner, String repositoryName) {
        RepositoryDetails repositoryToDelete = repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        repository.delete(repositoryToDelete);
    }

    public RepositoryPojo convertClientRepositoryToPojo(String owner, String repositoryName) {
        try {
            GitHubRepository gitHubRepository = client.getRepositoryDetails(owner, repositoryName)
                    .orElseThrow(() -> new DataNotFoundException("Repository not found"));
            return mapper.mapGitHubRepositoryToPojo(gitHubRepository);
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("Repository not found");
        }
    }

    public RepositoryPojo getDetails(String owner, String repositoryName) {
        var localRepository = repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        return mapper.mapRepositoryDetailsToPojo(localRepository);
    }
}