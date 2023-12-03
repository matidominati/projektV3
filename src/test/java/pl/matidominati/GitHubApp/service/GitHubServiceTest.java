package pl.matidominati.GitHubApp.service;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matidominati.GitHubApp.client.GitHubClient;
import pl.matidominati.GitHubApp.client.model.GitHubOwner;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.mapper.RepositoryMapper;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GitHubServiceTest {

    private GitHubClient client;
    private RepositoryMapper mapper;
    private GitHubService service;

    @BeforeEach
    void setup() {
        this.client = Mockito.mock(GitHubClient.class);
        this.mapper = Mappers.getMapper(RepositoryMapper.class);
        this.service = new GitHubService(client, mapper);
    }

    @Test
    void getClientRepositoryDetails_ValidOwnerAndRepository_ReturnsClientRepositoryDetails() {
        GitHubOwner gitHubOwner = GitHubOwner.builder()
                .login("qqqqq")
                .id(1L)
                .build();
        GitHubRepository gitHubRepository = GitHubRepository.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .gitHubOwner(gitHubOwner)
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.now().minusDays(5))
                .id(1L)
                .stargazersCount(5)
                .build();

        when(client.getRepositoryDetails(gitHubOwner.getLogin(), gitHubRepository.getName())).thenReturn(Optional.of(gitHubRepository));
        var expectedPojo = mapper.mapGitHubRepositoryToPojo(gitHubRepository);
        var expectedDto = mapper.mapPojoToDto(expectedPojo);

        RepositoryResponseDto resultDto = service.getClientRepositoryDetails(gitHubOwner.getLogin(), gitHubRepository.getName());

        assertEquals(resultDto.getFullName(), expectedDto.getFullName());
        assertEquals(resultDto.getCloneUrl(), expectedDto.getCloneUrl());
        assertEquals(resultDto.getDescription(), expectedDto.getDescription());
        assertEquals(resultDto.getStars(), expectedDto.getStars());
        assertEquals(resultDto.getCreatedAt(), expectedDto.getCreatedAt());

        verify(client).getRepositoryDetails(gitHubOwner.getLogin(), gitHubRepository.getName());
    }

    @Test
    void getClientRepositoryDetails_InvalidOwnerOrRepository_ThrowsDataNotFoundException() {
        GitHubOwner gitHubOwner = GitHubOwner.builder()
                .login("qqqqq")
                .id(1L)
                .build();
        GitHubRepository gitHubRepository = GitHubRepository.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .gitHubOwner(gitHubOwner)
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.now().minusDays(5))
                .id(1L)
                .stargazersCount(5)
                .build();

        when(client.getRepositoryDetails(gitHubOwner.getLogin(), gitHubRepository.getName()))
                .thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.convertClientRepositoryToPojo(gitHubOwner.getLogin(), gitHubRepository.getName()));
        verify(client).getRepositoryDetails(gitHubOwner.getLogin(), gitHubRepository.getName());
    }

    @Test
    void getClientRepositories_ValidUsername_ReturnsRepositoryList() {
        GitHubOwner gitHubOwner = GitHubOwner.builder()
                .login("qqqqq")
                .id(1L)
                .build();
        GitHubRepository gitHubRepository = GitHubRepository.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .gitHubOwner(gitHubOwner)
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.now().minusDays(5))
                .id(1L)
                .stargazersCount(5)
                .build();
        List<GitHubRepository> gitHubRepositories = List.of(gitHubRepository, gitHubRepository);
        when(client.getRepositories(gitHubOwner.getLogin())).thenReturn(gitHubRepositories);

        List<RepositoryResponseDto> resultDtos = service.getClientRepositories(gitHubOwner.getLogin());

        assertEquals(resultDtos.size(), gitHubRepositories.size());
    }

    @Test
    void getClientRepositories_UserNotFound_ThrowsDataNotFoundException() {
        GitHubOwner gitHubOwner = GitHubOwner.builder()
                .login("qqqqq")
                .id(1L)
                .build();

        when(client.getRepositories(gitHubOwner.getLogin())).thenThrow(FeignException.NotFound.class);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> service.getClientRepositories(gitHubOwner.getLogin()));

        assertEquals("User not found", exception.getMessage());
        verify(client).getRepositories(gitHubOwner.getLogin());
    }
}