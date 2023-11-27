package pl.matidominati.GitHubApp.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.matidominati.GitHubApp.model.GithubRepo;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "githubApp", url = "https://api.github.com")
public interface GitHubFeignClient {

    @GetMapping("/repos/{owner}/{repo}")
    Optional<GithubRepo> getRepositoryDetails(@PathVariable String owner, @PathVariable String repo);

    @GetMapping("/users/{username}/repos")
    Optional<List<GithubRepo>> getRepositories(@PathVariable String username);
}
