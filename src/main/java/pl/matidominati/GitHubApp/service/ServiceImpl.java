package pl.matidominati.GitHubApp.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.model.GithubRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceImpl {

    private final GitHubFeignClient githubFeignClient;

    public GithubRepo getRepositoryDetails(String owner, String repositoryName) {
        Optional<GithubRepo> githubRepo = githubFeignClient.getRepositoryDetails(owner, repositoryName);
        if (githubRepo.isEmpty()) {
            throw new DataNotFoundException("Incorrect owner or repository name provided");
        }
        return githubRepo.get();
    }

    public List<GithubRepo> getRepository(String username) {
        try {
            return githubFeignClient.getRepositories(username).orElse(Collections.emptyList());
        } catch (FeignException.NotFound e) {
            throw new DataNotFoundException("User not found");
        }
    }
}
