package pl.matidominati.GitHubApp.service;

import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matidominati.GitHubApp.client.GitHubFeignClient;
import pl.matidominati.GitHubApp.client.model.GitHubRepo;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private GitHubFeignClient gitHubFeignClient;

    @InjectMocks
    private ServiceImpl service;

    @Test
    void getRepositoryDetails_ValidOwnerAndRepository_ReturnsRepositoryDetails() {

        String owner = "xyz";
        String repositoryName = "abc";
        GitHubRepo gitHubRepo = new GitHubRepo();

        when(gitHubFeignClient.getRepositoryDetails(owner, repositoryName)).thenReturn(Optional.of(gitHubRepo));

        GitHubRepo result = service.getRepositoryDetails(owner, repositoryName);

        assertNotNull(result);
        assertEquals(gitHubRepo, result);

        verify(gitHubFeignClient).getRepositoryDetails(owner, repositoryName);
    }

    @Test
    void getRepositoryDetails_InvalidOwnerOrRepository_ThrowsDataNotFoundException() {

        String owner = "abc";
        String repositoryName = "xyz";

        when(gitHubFeignClient.getRepositoryDetails(owner, repositoryName))
                .thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> service.getRepositoryDetails(owner, repositoryName));
        assertEquals("Incorrect owner or repository name provided", exception.getMessage());

        verify(gitHubFeignClient).getRepositoryDetails(owner, repositoryName);
    }

    @Test
    void getRepository_ValidUsername_ReturnsRepoList() {

        String username = "abc";
        GitHubRepo gitHubRepo = new GitHubRepo();
        List<GitHubRepo> repoList = List.of(gitHubRepo, gitHubRepo);

        when(gitHubFeignClient.getRepositories(username)).thenReturn(Optional.of(repoList));

        List<GitHubRepo> result = service.getRepository(username);

        assertNotNull(result);
        assertEquals(repoList, result);

        verify(gitHubFeignClient).getRepositories(username);
    }

    @Test
    void getRepository_UserNotFound_ThrowsDataNotFoundException() {

        String username = "abc";

        when(gitHubFeignClient.getRepositories(username)).thenThrow(FeignException.NotFound.class);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> service.getRepository(username));

        assertEquals("User not found", exception.getMessage());
        verify(gitHubFeignClient).getRepositories(username);
    }
}



