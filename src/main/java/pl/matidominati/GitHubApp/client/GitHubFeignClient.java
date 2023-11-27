package pl.matidominati.GitHubApp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.matidominati.GitHubApp.client.model.GitHubRepo;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "githubApp", url = "https://api.github.com")
public interface GitHubFeignClient {

    @GetMapping("/repos/{owner}/{repo}")
    Optional<GitHubRepo> getRepositoryDetails(@PathVariable String owner, @PathVariable String repo);

    @GetMapping("/users/{username}/repos")
    Optional<List<GitHubRepo>> getRepositories(@PathVariable String username);
}
