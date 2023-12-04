package pl.matidominati.GitHubApp.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matidominati.GitHubApp.client.GitHubClient;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.mapper.RepositoryMapper;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubService {
    private final GitHubClient client;
    private final RepositoryMapper mapper;

    public RepositoryResponseDto getClientRepositoryDetails(String owner, String repositoryName) {
        RepositoryPojo repositoryPojo = convertClientRepositoryToPojo(owner, repositoryName);
        return mapper.mapPojoToDto(repositoryPojo);
    }

    public List<RepositoryResponseDto> getClientRepositories(String owner) {
        List<RepositoryPojo> repositoryPojos;
        try {
            repositoryPojos = client.getRepositories(owner).stream()
                    .map(mapper::mapGitHubRepositoryToPojo)
                    .toList();
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("User not found");
        }
        return repositoryPojos.stream()
                .map(mapper::mapPojoToDto)
                .toList();
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
}