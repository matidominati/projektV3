package pl.matidominati.GitHubApp.controller;

import pl.matidominati.GitHubApp.client.model.GitHubOwner;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;

import java.time.LocalDateTime;

public final class DataFactory {
    public static GitHubRepository createGitHubRepository() {
        return GitHubRepository.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .cloneUrl("bbbb.com")
                .id(1L)
                .stargazersCount(5)
                .gitHubOwner(GitHubOwner.builder()
                        .id(1L)
                        .login("user")
                        .build())
                .build();
    }

    public static RepositoryPojo createUpdatedRepositoryDetails() {
        return RepositoryPojo.builder()
                .fullName("XXXXX")
                .description("DDDDD")
                .cloneUrl("YYY.com")
                .createdAt(LocalDateTime.of(2020, 11, 23, 10, 19, 22))
                .stars(100)
                .build();
    }

    public static RepositoryDetails createRepositoryDetails() {
        return RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("bbbb.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("user")
                .build();
    }
}

