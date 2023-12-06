package pl.matidominati.GitHubApp.service;

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
                .gitHubOwner(GitHubOwner.builder()
                        .login("qqqqq")
                        .id(1L)
                        .build())
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.now().minusDays(5))
                .id(1L)
                .stargazersCount(5)
                .build();
    }

    public static RepositoryDetails createRepositoryDetails() {
        return RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername(createGitHubRepository().getGitHubOwner().getLogin())
                .build();
    }

    public static RepositoryPojo createRepositoryPojo() {
        return RepositoryPojo.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .build();
    }
}
