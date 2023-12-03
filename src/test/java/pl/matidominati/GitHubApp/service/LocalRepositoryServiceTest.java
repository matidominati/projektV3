package pl.matidominati.GitHubApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matidominati.GitHubApp.client.model.GitHubOwner;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.exception.DataAlreadyExistsException;
import pl.matidominati.GitHubApp.exception.DataNotFoundException;
import pl.matidominati.GitHubApp.mapper.RepositoryMapper;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
import pl.matidominati.GitHubApp.repository.LocalRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocalRepositoryServiceTest {

    private GitHubService clientService;
    private RepositoryMapper mapper;
    private LocalRepository repository;
    private LocalRepositoryService localService;

    @BeforeEach
    void setup() {
        this.clientService = Mockito.mock(GitHubService.class);
        this.mapper = Mockito.mock(RepositoryMapper.class);
        this.repository = Mockito.mock(LocalRepository.class);
        this.localService = new LocalRepositoryService(mapper, repository, clientService);
    }

    @Test
    void saveRepositoryDetails_DataNotExists_ReturnsRepositoryResponseDto() {
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
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .id(1L)
                .stargazersCount(5)
                .build();
        RepositoryPojo repositoryPojo = RepositoryPojo.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .build();
        RepositoryResponseDto responseDto = RepositoryResponseDto.builder()
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .build();
        RepositoryDetails repositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername(gitHubOwner.getLogin())
                .build();

        when(clientService.convertClientRepositoryToPojo(gitHubOwner.getLogin(), gitHubRepository.getName())).thenReturn(repositoryPojo);
        when(repository.findByOwnerUsernameAndRepositoryName(gitHubOwner.getLogin(), gitHubRepository.getName())).thenReturn(Optional.empty());
        when(repository.save(any(RepositoryDetails.class))).thenReturn(repositoryDetails);
        when(mapper.mapRepositoryDetailsToResponseDto(any(RepositoryDetails.class))).thenReturn(responseDto);

        var resultDto = localService.saveRepositoryDetails(gitHubOwner.getLogin(), gitHubRepository.getName());

        assertEquals(resultDto.getFullName(), repositoryDetails.getFullName());
        assertEquals(resultDto.getDescription(), repositoryDetails.getDescription());
        assertEquals(resultDto.getStars(), repositoryDetails.getStars());
        assertEquals(resultDto.getCreatedAt(), repositoryDetails.getCreatedAt());
        assertEquals(resultDto.getCloneUrl(), repositoryDetails.getCloneUrl());
        verify(repository, times(1)).save(any(RepositoryDetails.class));
    }

    @Test
    void saveRepositoryDetails_DataExists_ThrowDataAlreadyExistsException() {
        RepositoryDetails repositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("qqqqq")
                .build();

        when(repository.findByOwnerUsernameAndRepositoryName(repositoryDetails.getOwnerUsername(),
                repositoryDetails.getName())).thenReturn(Optional.of(repositoryDetails));

        DataAlreadyExistsException exception = assertThrows(DataAlreadyExistsException.class, () -> localService.saveRepositoryDetails(repositoryDetails.getOwnerUsername(), repositoryDetails.getName()));
        assertEquals("This resource is in the database", exception.getMessage());
    }

    @Test
    void getLocalRepositoryDetails_DataExists_ReturnRepositoryResponseDto() {
        RepositoryDetails repositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("qqqqq")
                .build();
        RepositoryResponseDto responseDto = RepositoryResponseDto.builder()
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .build();

        when(repository.findByOwnerUsernameAndRepositoryName(repositoryDetails.getOwnerUsername(), repositoryDetails.getName())).thenReturn(Optional.of(repositoryDetails));
        when(mapper.mapRepositoryDetailsToResponseDto(any(RepositoryDetails.class))).thenReturn(responseDto);

        var resultDto = localService.getLocalRepositoryDetails(repositoryDetails.getOwnerUsername(), repositoryDetails.getName());

        assertEquals(resultDto.getFullName(), responseDto.getFullName());
        assertEquals(resultDto.getCloneUrl(), responseDto.getCloneUrl());
        assertEquals(resultDto.getDescription(), responseDto.getDescription());
        assertEquals(resultDto.getStars(), responseDto.getStars());
        assertEquals(resultDto.getCreatedAt(), responseDto.getCreatedAt());
    }

    @Test
    void getLocalRepositoryDetails_DataNotExists_ThrowDataNotFoundException() {
        RepositoryDetails repositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("qqqqq")
                .build();

        when(repository.findByOwnerUsernameAndRepositoryName(repositoryDetails.getOwnerUsername(), repositoryDetails.getName())).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> localService.getLocalRepositoryDetails(repositoryDetails.getOwnerUsername(), repositoryDetails.getName()));

        assertEquals("Incorrect username or repository name provided.", exception.getMessage());
    }

    @Test
    void editLocalRepositoryDetails_DataExists_ReturnUpdatedRepositoryResponseDto() {
        RepositoryDetails originalRepository = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("qqqqq")
                .build();
        RepositoryPojo repositoryPojo = RepositoryPojo.builder()
                .name("aaaaa")
                .fullName("B2")
                .description("C3")
                .cloneUrl("xxx.com")
                .createdAt(LocalDateTime.of(2020, 11, 23, 10, 19, 22))
                .stars(3)
                .build();
        RepositoryDetails updatedRepository = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("B2")
                .description("C3")
                .cloneUrl("xxx.com")
                .createdAt(LocalDateTime.of(2020, 11, 23, 10, 19, 22))
                .stars(3)
                .build();
        RepositoryResponseDto responseDto = RepositoryResponseDto.builder()
                .fullName("B2")
                .description("C3")
                .cloneUrl("xxx.com")
                .createdAt(LocalDateTime.of(2020, 11, 23, 10, 19, 22))
                .stars(3)
                .build();

        when(repository.findByOwnerUsernameAndRepositoryName(originalRepository.getOwnerUsername(), originalRepository.getName())).thenReturn(Optional.of(originalRepository));
        when(mapper.mapRepositoryDetailsToPojo(any(RepositoryDetails.class))).thenReturn(repositoryPojo);
        when(mapper.mapPojoToRepositoryDetails(any(RepositoryPojo.class))).thenReturn(updatedRepository);
        when(repository.save(any(RepositoryDetails.class))).thenReturn(updatedRepository);
        when(mapper.mapRepositoryDetailsToResponseDto(any(RepositoryDetails.class))).thenReturn(responseDto);

        var result = localService.editLocalRepositoryDetails(originalRepository.getOwnerUsername(), originalRepository.getName(), repositoryPojo);

        assertEquals(result.getFullName(), responseDto.getFullName());
        assertEquals(result.getCloneUrl(), responseDto.getCloneUrl());
        assertEquals(result.getDescription(), responseDto.getDescription());
        assertEquals(result.getStars(), responseDto.getStars());
        assertEquals(result.getCreatedAt(), responseDto.getCreatedAt());
    }

    @Test
    void editLocalRepositoryDetails_DataDoesNotExist_ReturnUpdatedRepositoryResponseDto() {
        RepositoryDetails repositoryDetails = new RepositoryDetails();
        RepositoryPojo repositoryPojo = new RepositoryPojo();

        when(repository.findByOwnerUsernameAndRepositoryName(repositoryDetails.getOwnerUsername(), repositoryDetails.getName())).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> localService.editLocalRepositoryDetails(repositoryDetails.getOwnerUsername(), repositoryDetails.getName(), repositoryPojo));
        assertEquals("Incorrect username or repository name provided.", exception.getMessage());
    }

    @Test
    void deleteLocalRepositoryDetails_RepositoryExists_DeletedRepository() {
        RepositoryDetails repositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("qqqqq")
                .build();
        when(repository.findByOwnerUsernameAndRepositoryName(repositoryDetails.getOwnerUsername(), repositoryDetails.getName())).thenReturn(Optional.of(repositoryDetails));

        localService.deleteLocalRepositoryDetails(repositoryDetails.getOwnerUsername(), repositoryDetails.getName());

        verify(repository, times(1)).delete(repositoryDetails);
    }

    @Test
    void deleteLocalRepositoryDetails_RepositoryDoesNotExist_ThrowsDataNotFoundException() {
        RepositoryDetails repositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("qqqqq")
                .build();

        when(repository.findByOwnerUsernameAndRepositoryName(repositoryDetails.getOwnerUsername(), repositoryDetails.getName())).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> localService.deleteLocalRepositoryDetails(repositoryDetails.getOwnerUsername(), repositoryDetails.getName()));
        assertEquals("Incorrect username or repository name provided.", exception.getMessage());
    }
}