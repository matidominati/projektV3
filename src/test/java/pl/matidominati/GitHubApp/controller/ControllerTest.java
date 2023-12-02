//package pl.matidominati.GitHubApp.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import pl.matidominati.GitHubApp.exception.DataAlreadyExistsException;
//import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
//import pl.matidominati.GitHubApp.model.dto.UpdateRepositoryResponseDto;
//import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
//import pl.matidominati.GitHubApp.service.GitHubService;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@AutoConfigureMockMvc
//@WebMvcTest(GitHubController.class)
//public class ControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private GitHubService gitHubService;
//
//    @Test
//    void getClientRepositoryDetails_ReturnsRepositoryResponseDto() throws Exception {
//        String owner = "testOwner";
//        String repoName = "testRepo";
//        RepositoryResponseDto responseDto = new RepositoryResponseDto();
//        when(gitHubService.getClientRepositoryDetails(owner, repoName)).thenReturn(responseDto);
//        mockMvc.perform(get("/repositories/{gitHubOwner}/{repoName}", owner, repoName))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void getClientRepositories_ReturnsListOfRepositories() throws Exception {
//        String gitHubOwner = "abc";
//        List<RepositoryResponseDto> expectedRepositories = List.of(new RepositoryResponseDto(), new RepositoryResponseDto());
//        when(gitHubService.getClientRepositories(gitHubOwner)).thenReturn(expectedRepositories);
//
//        mockMvc.perform(get("/repositories/{gitHubOwner}", gitHubOwner)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)));
//
//        verify(gitHubService, times(1)).getClientRepositories(gitHubOwner);
//    }
//    @Test
//    void getLocalRepository_DataExists_ReturnsRepositoryResponseDto() throws Exception {
//        String owner = "abc";
//        String repoName = "xyz";
//        RepositoryResponseDto expectedDto = new RepositoryResponseDto();
//        when(gitHubService.getLocalRepositoryDetails(owner, repoName)).thenReturn(expectedDto);
//
//        mockMvc.perform(get("/local/repositories/{owner}/{repoName}", owner, repoName)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//        verify(gitHubService, times(1)).getLocalRepositoryDetails(owner, repoName);
//    }
//    @Test
//    void saveRepository_RepositorySaved_ReturnsUpdateRepositoryResponseDto() throws Exception {
//        String owner = "abc";
//        String repoName = "xyz";
//        UpdateRepositoryResponseDto expectedDto = new UpdateRepositoryResponseDto();
//        when(gitHubService.saveRepositoryDetails(owner, repoName)).thenReturn(expectedDto);
//
//        mockMvc.perform(post("/repositories/{owner}/{repoName}", owner, repoName)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//
//        verify(gitHubService, times(1)).saveRepositoryDetails(owner, repoName);
//    }
//
//    @Test
//    void saveRepository_RepositoryAlreadyExists_ReturnsErrorMessage() throws Exception {
//        String owner = "abc";
//        String repoName = "xyz";
//        when(gitHubService.saveRepositoryDetails(owner, repoName)).thenThrow(new DataAlreadyExistsException("Data already exists"));
//
//        mockMvc.perform(post("/repositories/{owner}/{repoName}", owner, repoName)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isConflict())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Data already exists"));
//
//        verify(gitHubService, times(1)).saveRepositoryDetails(owner, repoName);
//    }
//    @Test
//    void deleteRepository_ValidInput_ReturnsOk() throws Exception {
//        String owner = "abc";
//        String repoName = "xyz";
//
//        mockMvc.perform(delete("/repositories/{owner}/{repoName}", owner, repoName))
//                .andExpect(status().isOk())
//                .andExpect(content().string(""));
//
//        verify(gitHubService, times(1)).deleteLocalRepositoryDetails(owner, repoName);
//    }
//}
//
//
