package pl.matidominati.GitHubApp.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matidominati.GitHubApp.client.GitHubFeignClient;
import pl.matidominati.GitHubApp.client.model.GitHubRepo;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceImpl {

    private final GitHubFeignClient gitHubFeignClient;

    public GitHubRepo getRepositoryDetails(String owner, String repositoryName) {
        Optional<GitHubRepo> githubRepo = gitHubFeignClient.getRepositoryDetails(owner, repositoryName);
        if (githubRepo.isEmpty()) {
            throw new DataNotFoundException("Incorrect owner or repository name provided");
        }
        return githubRepo.get();
    }

    public List<GitHubRepo> getRepository(String username) {
        try {
            return gitHubFeignClient.getRepositories(username).orElse(Collections.emptyList());
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("User not found");
        }
    }
}
