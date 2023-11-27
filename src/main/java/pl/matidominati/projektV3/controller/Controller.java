package pl.matidominati.projektV3.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matidominati.projektV3.model.GithubRepo;
import pl.matidominati.projektV3.service.ServiceImpl;

@RestController
@RequestMapping("/github-details")
@RequiredArgsConstructor
public class Controller {

    private final ServiceImpl service;

    @GetMapping("/{owner}/{repo}")
    public GithubRepo getRepositoryDetails(@PathVariable String owner, @PathVariable String repo) {
        return service.getGithubDetails(owner, repo);
    }
}
