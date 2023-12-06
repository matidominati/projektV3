package pl.matidominati.GitHubApp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "GitHubApp", url = "${app.github.api.url}")
public interface GitHubClient {

    @GetMapping("/repos/{gitHubOwner}/{repo}")
    Optional<GitHubRepository> getRepositoryDetails(@PathVariable String gitHubOwner, @PathVariable String repo);

    @GetMapping("/users/{username}/repos")
    List<GitHubRepository> getRepositories(@PathVariable String username);
}
