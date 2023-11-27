package pl.matidominati.projektV3.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.matidominati.projektV3.model.GithubRepo;

@FeignClient(value = "githubApp", url = "https://api.github.com")
public interface GithubFeignClient {

    @GetMapping("repos/{owner}/{repo}")
    GithubRepo getRepositoryDetails(@PathVariable String owner, @PathVariable String repo);

//    String getOwnerRepositories(String owner);
}
