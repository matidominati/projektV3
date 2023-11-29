package pl.matidominati.GitHubApp.service;

import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.matidominati.GitHubApp.mapper.RepoMapper;
import pl.matidominati.GitHubApp.model.dto.RepoResponseDto;
import pl.matidominati.GitHubApp.model.entity.Repo;
import org.springframework.stereotype.Service;
import pl.matidominati.GitHubApp.client.GitHubFeignClient;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.repository.RepoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubFeignClient gitHubFeignClient;
    private final RepoMapper repoMapper;
    private final RepoRepository repoRepository;

    public RepoResponseDto getRepositoryDetails(String owner, String repositoryName) {
        Optional<GitHubRepository> githubRepo = gitHubFeignClient.getRepositoryDetails(owner, repositoryName);
        try {
            return repoMapper.mapToDto(githubRepo.get());
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("User not found");
        }
    }

    public List<RepoResponseDto> getRepositories(String username) {
        try {
            return gitHubFeignClient.getRepositories(username).stream()
                    .map(repoMapper::mapToDto)
                    .collect(Collectors.toList());
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("User not found");
        }
    }

    @Transactional
    public RepoResponseDto saveRepoDetails(String owner, String repositoryName) {
        var repoRequestDto = repoMapper.mapToRequest(getRepositoryDetails(owner, repositoryName));
        var repo = new Repo();
        repo.setDescription(repoRequestDto.getDescription());
        repo.setCreatedAt(repoRequestDto.getCreatedAt());
        repo.setStars(repoRequestDto.getStars());
        repo.setCloneUrl(repoRequestDto.getCloneUrl());
        repo.setRepoName(repoRequestDto.getFullName());
        repoRepository.save(repo);
        return repoMapper.mapToDto(repo);
    }

    public RepoResponseDto getRepoDetails(String owner, String repositoryName) {
        var repo = repoRepository.findByRepoNameAndOwner(repositoryName, owner);
        if (repo.isEmpty()) {
            throw new DataNotFoundException("Repository not found");
        }
        return repoMapper.mapToDto(repo.get());
    }
}
