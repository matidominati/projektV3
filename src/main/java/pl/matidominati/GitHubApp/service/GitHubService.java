package pl.matidominati.GitHubApp.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matidominati.GitHubApp.client.GitHubClient;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.mapper.RepositoryMapper;
import pl.matidominati.GitHubApp.model.dto.RepoResponseDto;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
import pl.matidominati.GitHubApp.repository.RepoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubClient gitHubClient;
    private final RepositoryMapper repositoryMapper;
    private final RepoRepository repoRepository;

    public RepoResponseDto getRepositoryDetails(String owner, String repositoryName) {
        RepositoryPojo repositoryPojo = getRepositoryPojo(owner, repositoryName);
        return repositoryMapper.pojoToDto(repositoryPojo);
    }

    public List<RepoResponseDto> getRepositories(String username) {
        try {
            List<RepositoryPojo> pojos = gitHubClient.getRepositories(username).stream()
                    .map(repositoryMapper::mapToPojo)
                    .toList();
            return pojos.stream()
                    .map(repositoryMapper::pojoToDto)
                    .toList();
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("User not found");
        }
    }

    @Transactional
    public RepoResponseDto saveRepoDetails(String username, String repositoryName) {
        var repoRequestDto = repositoryMapper.mapToRequest(getRepositoryPojo(username, repositoryName));
        var repo = new RepositoryDetails();
        repo.setDescription(repoRequestDto.getDescription());
        repo.setCreatedAt(repoRequestDto.getCreatedAt());
        repo.setStars(repoRequestDto.getStars());
        repo.setCloneUrl(repoRequestDto.getCloneUrl());
        repo.setFullName(repoRequestDto.getFullName());
        repo.setName(repoRequestDto.getName());
        repo.setOwnerUsername(repoRequestDto.getOwnerUsername());
        repoRepository.save(repo);
        return repositoryMapper.mapToResponseDto(repo);
    }

    public RepoResponseDto getRepoDetails(String ownerUsername, String repositoryName) {
        var repoDetails = repoRepository.findByOwnerUsernameAndRepositoryName(ownerUsername, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        return repositoryMapper.mapToResponseDto(repoDetails);
    }

    @Transactional
    public RepoResponseDto editRepoDetails(String ownerUsername, String repositoryName, RepositoryPojo repositoryPojo) {
        var repositoryToEdit = getDetails(ownerUsername, repositoryName);
        if (repositoryPojo.getFullName() != null && !repositoryToEdit.getFullName().equals(repositoryPojo.getFullName())) {
            repositoryToEdit.setFullName(repositoryPojo.getFullName());
        }
        if (repositoryPojo.getDescription() != null && !repositoryToEdit.getDescription().equals(repositoryPojo.getDescription())) {
            repositoryToEdit.setDescription(repositoryPojo.getDescription());
        }
        if (repositoryPojo.getCloneUrl() != null && !repositoryToEdit.getCloneUrl().equals(repositoryPojo.getCloneUrl())) {
            repositoryToEdit.setCloneUrl(repositoryPojo.getCloneUrl());
        }
        if (repositoryToEdit.getStars() != repositoryPojo.getStars()) {
            repositoryToEdit.setStars(repositoryPojo.getStars());
        }
        if (repositoryPojo.getCreatedAt() != null && !repositoryToEdit.getCreatedAt().equals(repositoryPojo.getCreatedAt())
                && !repositoryPojo.getCreatedAt().isAfter(LocalDateTime.now())) {
            repositoryToEdit.setCreatedAt(repositoryPojo.getCreatedAt());
        }
        var repoToSave = repositoryMapper.PojoToRepositoryDetails(repositoryToEdit);
        repoRepository.save(repoToSave);
        return repositoryMapper.pojoToDto(repositoryPojo);
    }

    @Transactional
    public void deleteRepositoryDetails(String ownerUsername, String repositoryName) {
        RepositoryDetails repositoryToDelete = repoRepository.findByOwnerUsernameAndRepositoryName(ownerUsername, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        repoRepository.delete(repositoryToDelete);
    }

    public RepositoryPojo getRepositoryPojo(String owner, String repositoryName) {
        try {
            GitHubRepository githubRepo = gitHubClient.getRepositoryDetails(owner, repositoryName)
                    .orElseThrow(() -> new DataNotFoundException("Repository not found"));
            return repositoryMapper.mapToPojo(githubRepo);
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("Repository not found");
        }
    }

    public RepositoryPojo getDetails(String ownerUsername, String repositoryName) {
        var repoDetails = repoRepository.findByOwnerUsernameAndRepositoryName(ownerUsername, repositoryName)
                .orElseThrow(() -> new DataNotFoundException("Incorrect username or repository name provided."));
        return repositoryMapper.RepositoryDetailsToPojo(repoDetails);
    }
}