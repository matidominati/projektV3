package pl.matidominati.GitHubApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matidominati.GitHubApp.model.GithubRepo;
import pl.matidominati.GitHubApp.service.ServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/github-details")
@RequiredArgsConstructor
public class Controller {

    private final ServiceImpl service;

    @GetMapping("/{owner}/{repo}")
    public GithubRepo getRepositoryDetails(@PathVariable String owner, @PathVariable String repo) {
        return service.getRepositoryDetails(owner, repo);
    }

    @GetMapping("/{owner}")
    public List<GithubRepo> getRepositories(@PathVariable String owner) {
        return service.getRepository(owner);
    }
}