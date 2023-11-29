//package pl.matidominati.GitHubApp.service;
//
//import feign.FeignException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import pl.matidominati.GitHubApp.client.GitHubFeignClient;
//import pl.matidominati.GitHubApp.client.model.Repo;
//import pl.matidominati.GitHubApp.exception.DataNotFoundException;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ServiceTest {
//
//    @Mock
//    private GitHubFeignClient gitHubFeignClient;
//
//    @InjectMocks
//    private GitHubService service;
//
//    @Test
//    void getRepositoryDetails_ValidOwnerAndRepository_ReturnsRepositoryDetails() {
//
//        String gitHubOwner = "xyz";
//        String repositoryName = "abc";
//        Repo gitHubRepo = new Repo();
//
//        when(gitHubFeignClient.getRepositoryDetails(gitHubOwner, repositoryName)).thenReturn(Optional.of(gitHubRepo));
//
//        Repo result = service.getRepositoryDetails(gitHubOwner, repositoryName);
//
//        assertNotNull(result);
//        assertEquals(gitHubRepo, result);
//
//        verify(gitHubFeignClient).getRepositoryDetails(gitHubOwner, repositoryName);
//    }
//
//    @Test
//    void getRepositoryDetails_InvalidOwnerOrRepository_ThrowsDataNotFoundException() {
//
//        String gitHubOwner = "abc";
//        String repositoryName = "xyz";
//
//        when(gitHubFeignClient.getRepositoryDetails(gitHubOwner, repositoryName))
//                .thenReturn(Optional.empty());
//
//        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> service.getRepositoryDetails(gitHubOwner, repositoryName));
//        assertEquals("Incorrect gitHubOwner or repository name provided", exception.getMessage());
//
//        verify(gitHubFeignClient).getRepositoryDetails(gitHubOwner, repositoryName);
//    }
//
//    @Test
//    void getRepository_ValidUsername_ReturnsRepoList() {
//
//        String username = "abc";
//        Repo gitHubRepo = new Repo();
//        List<Repo> repoList = List.of(gitHubRepo, gitHubRepo);
//
//        when(gitHubFeignClient.getRepositories(username)).thenReturn(Optional.of(repoList));
//
//        List<Repo> result = service.getRepository(username);
//
//        assertNotNull(result);
//        assertEquals(repoList, result);
//
//        verify(gitHubFeignClient).getRepositories(username);
//    }
//
//    @Test
//    void getRepository_UserNotFound_ThrowsDataNotFoundException() {
//
//        String username = "abc";
//
//        when(gitHubFeignClient.getRepositories(username)).thenThrow(FeignException.NotFound.class);
//
//        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> service.getRepository(username));
//
//        assertEquals("User not found", exception.getMessage());
//        verify(gitHubFeignClient).getRepositories(username);
//    }
//}
//
//
//
