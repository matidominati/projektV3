//package pl.matidominati.GitHubApp.service;
//
//import feign.FeignException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import pl.matidominati.GitHubApp.client.GitHubClient;
//import pl.matidominati.GitHubApp.client.model.GitHubRepository;
//import pl.matidominati.GitHubApp.exception.DataAlreadyExistsException;
//import pl.matidominati.GitHubApp.exception.DataNotFoundException;
//import pl.matidominati.GitHubApp.mapper.RepositoryMapper;
//import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
//import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
//import pl.matidominati.GitHubApp.repository.LocalRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ServiceTest {
//
//    private GitHubClient client;
//    private RepositoryMapper mapper;
//    private LocalRepository repository;
//    private GitHubService service;
//
//    @BeforeEach
//    void setup() {
//        this.client = Mockito.mock(GitHubClient.class);
//        this.mapper = Mappers.getMapper(RepositoryMapper.class);
//        this.repository = Mockito.mock(LocalRepository.class);
//        this.service = new GitHubService(client, mapper, repository);
//    }
//
//    @Test
//    void getClientRepositoryDetails_ValidOwnerAndRepository_ReturnsClientRepositoryDetails() {
//
//        String gitHubOwner = "xyz";
//        String repositoryName = "abc";
//        GitHubRepository gitHubRepository = new GitHubRepository();
//
//        when(client.getRepositoryDetails(gitHubOwner, repositoryName)).thenReturn(Optional.of(gitHubRepository));
//        var expectedPojo = mapper.mapGitHubRepositoryToPojo(gitHubRepository);
//        var expectedDto = mapper.mapPojoToDto(expectedPojo);
//
//        RepositoryResponseDto resultDto = service.getClientRepositoryDetails(gitHubOwner, repositoryName);
//
//        assertEquals(resultDto.getFullName(), expectedDto.getFullName());
//        assertEquals(resultDto.getCloneUrl(), expectedDto.getCloneUrl());
//        assertEquals(resultDto.getDescription(), expectedDto.getDescription());
//        assertEquals(resultDto.getStars(), expectedDto.getStars());
//        assertEquals(resultDto.getCreatedAt(), expectedDto.getCreatedAt());
//
//        verify(client).getRepositoryDetails(gitHubOwner, repositoryName);
//    }
//
//    @Test
//    void getClientRepositoryDetails_InvalidOwnerOrRepository_ThrowsDataNotFoundException() {
//
//        String gitHubOwner = "abc";
//        String repositoryName = "xyz";
//
//        when(client.getRepositoryDetails(gitHubOwner, repositoryName))
//                .thenReturn(Optional.empty());
//
//        assertThrows(DataNotFoundException.class, () -> service.convertClientRepositoryToPojo(gitHubOwner, repositoryName));
//        verify(client).getRepositoryDetails(gitHubOwner, repositoryName);
//    }
//
//    @Test
//    void getClientRepositories_ValidUsername_ReturnsRepositoryList() {
//
//        String owner = "abc";
//        GitHubRepository gitHubRepository1 = new GitHubRepository();
//        GitHubRepository gitHubRepository2 = new GitHubRepository();
//        List<GitHubRepository> gitHubRepositories = List.of(gitHubRepository1, gitHubRepository2);
//        when(client.getRepositories(owner)).thenReturn(gitHubRepositories);
//
//        List<RepositoryResponseDto> resultDtos = service.getClientRepositories(owner);
//
//        assertEquals(resultDtos.size(), gitHubRepositories.size());
//    }
//
//    @Test
//    void getClientRepositories_UserNotFound_ThrowsDataNotFoundException() {
//
//        String username = "abc";
//
//        when(client.getRepositories(username)).thenThrow(FeignException.NotFound.class);
//
//        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> service.getClientRepositories(username));
//
//        assertEquals("User not found", exception.getMessage());
//        verify(client).getRepositories(username);
//    }
//
//    @Test
//    void saveRepositoryDetails_DataNotExists_ReturnsRepositoryResponseDto() {
//        String owner = "abc";
//        String repositoryName = "xyz";
//        GitHubRepository gitHubRepository = new GitHubRepository();
//        when(client.getRepositoryDetails(owner, repositoryName)).thenReturn(Optional.of(gitHubRepository));
//        when(repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)).thenReturn(Optional.empty());
//
//        RepositoryDetails repositoryDetails = new RepositoryDetails();
//        when(repository.save(any(RepositoryDetails.class))).thenReturn(repositoryDetails);
//        RepositoryResponseDto resultDto = service.saveRepositoryDetails(owner, repositoryName);
//
//        verify(repository, times(1)).save(any(RepositoryDetails.class));
//    }
//
//    @Test
//    void saveRepositoryDetails_DataExists_ThrowDataAlreadyExistsException() {
//        String owner = "abc";
//        String repositoryName = "xyz";
//        GitHubRepository gitHubRepository = new GitHubRepository();
//        RepositoryDetails existingRepository = new RepositoryDetails();
//        when(client.getRepositoryDetails(owner, repositoryName)).thenReturn(Optional.of(gitHubRepository));
//        when(repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)).thenReturn(Optional.of(existingRepository));
//
//        assertThrows(DataAlreadyExistsException.class, () -> service.saveRepositoryDetails(owner, repositoryName));
//    }
//
//    @Test
//    void getLocalRepositoryDetails_DataExists_ReturnRepositoryResponseDto() {
//        String owner = "abc";
//        String repositoryName = "xyz";
//        RepositoryDetails repositoryDetails = new RepositoryDetails();
//        when(repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)).thenReturn(Optional.of(repositoryDetails));
//        var expectedPojo = mapper.mapRepositoryDetailsToPojo(repositoryDetails);
//        var expectedDto = mapper.mapPojoToDto(expectedPojo);
//
//        RepositoryResponseDto resultDto = service.getLocalRepositoryDetails(owner, repositoryName);
//
//        assertEquals(resultDto.getFullName(), expectedDto.getFullName());
//        assertEquals(resultDto.getCloneUrl(), expectedDto.getCloneUrl());
//        assertEquals(resultDto.getDescription(), expectedDto.getDescription());
//        assertEquals(resultDto.getStars(), expectedDto.getStars());
//        assertEquals(resultDto.getCreatedAt(), expectedDto.getCreatedAt());
//    }
//
//    @Test
//    void getLocalRepositoryDetails_DataNotExists_ThrowDataNotFoundException() {
//        String owner = "abc";
//        String repositoryName = "xyz";
//        when(repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)).thenReturn(Optional.empty());
//
//        assertThrows(DataNotFoundException.class, () -> service.getLocalRepositoryDetails(owner, repositoryName));
//    }
//
//    @Test
//    void editLocalRepositoryDetails_DataExists_ReturnUpdatedRepositoryResponseDto() {
//        String owner = "abc";
//        String repositoryName = "xyz";
//        RepositoryDetails existingRepository = new RepositoryDetails();
//        Mockito.when(repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)).thenReturn(Optional.of(existingRepository));
//        var repositoryPojo = mapper.mapRepositoryDetailsToPojo(existingRepository);
//        var repoDetails = mapper.mapPojoToRepositoryDetails(repositoryPojo);
//
//        var updateDetails = mapper.mapPojoToUpdateRepositoryResponseDto(repositoryPojo);
//
//        RepositoryResponseDto resultDto = service.editLocalRepositoryDetails(owner, repositoryName, repositoryPojo);
//
//        assertEquals(resultDto.getFullName(), updateDetails.getFullName());
//        assertEquals(resultDto.getCloneUrl(), updateDetails.getCloneUrl());
//        assertEquals(resultDto.getDescription(), updateDetails.getDescription());
//        assertEquals(resultDto.getStars(), updateDetails.getStars());
//        assertEquals(resultDto.getCreatedAt(), updateDetails.getCreatedAt());
//    }
//
//    @Test
//    void deleteLocalRepositoryDetails_ValidOwnerAndRepository_DeletesRepository() {
//        String owner = "abc";
//        String repositoryName = "xyz";
//        RepositoryDetails repositoryDetails = new RepositoryDetails();
//        when(repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)).thenReturn(Optional.of(repositoryDetails));
//
//        service.deleteLocalRepositoryDetails(owner, repositoryName);
//
//        verify(repository, Mockito.times(1)).findByOwnerUsernameAndRepositoryName(owner, repositoryName);
//        verify(repository, Mockito.times(1)).delete(repositoryDetails);
//    }
//
//    @Test
//    void deleteLocalRepositoryDetails_InvalidOwnerOrRepository_ThrowsDataNotFoundException() {
//
//        String owner = "abc";
//        String repositoryName = "xyz";
//        when(repository.findByOwnerUsernameAndRepositoryName(owner, repositoryName)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        assertThrows(DataNotFoundException.class, () -> service.deleteLocalRepositoryDetails(owner, repositoryName));
//    }
//}