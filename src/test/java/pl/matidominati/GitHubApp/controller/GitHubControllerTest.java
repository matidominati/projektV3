package pl.matidominati.GitHubApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.matidominati.GitHubApp.client.GitHubClient;
import pl.matidominati.GitHubApp.client.model.GitHubOwner;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GitHubControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GitHubClient client;

    @Test
    void shouldGetGitHubRepositoryDetails() throws Exception {
        GitHubRepository gitHubRepository = GitHubRepository.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc CCCCC")
                .createdAt(LocalDateTime.of(2015, 10, 10, 10, 10))
                .cloneUrl("bbbb.com")
                .id(1L)
                .gitHubOwner(GitHubOwner.builder()
                        .id(1L)
                        .login("user")
                        .build())
                .build();

        when(client.getRepositoryDetails(any(), any())).thenReturn(Optional.ofNullable(gitHubRepository));

        MvcResult mvcResult = mockMvc.perform(get("/repositories/{owner}/{repoName}", gitHubRepository.getGitHubOwner().getLogin(), gitHubRepository.getName()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        RepositoryResponseDto responseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RepositoryResponseDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getFullName()).isEqualTo(gitHubRepository.getFullName());
        assertThat(responseDto.getDescription()).isEqualTo(gitHubRepository.getDescription());
        assertThat(responseDto.getCloneUrl()).isEqualTo(gitHubRepository.getCloneUrl());
        assertThat(responseDto.getCreatedAt()).isEqualTo(gitHubRepository.getCreatedAt());
        assertThat(responseDto.getStars()).isEqualTo(gitHubRepository.getStargazersCount());
    }

    @Test
    void shouldGetClientRepositoriesList() throws Exception {

        GitHubOwner gitHubOwner = new GitHubOwner(1L, "user");

        GitHubRepository gitHubRepositoryA = GitHubRepository.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc CCCCC")
                .createdAt(LocalDateTime.of(2015, 10, 10, 10, 10))
                .cloneUrl("bbbb.com")
                .id(1L)
                .gitHubOwner(gitHubOwner)
                .build();

        GitHubRepository gitHubRepositoryB = GitHubRepository.builder()
                .name("aaaaappp")
                .fullName("bbbbbpp")
                .description("cccccppp CCCCC")
                .createdAt(LocalDateTime.of(2015, 9, 10, 10, 10))
                .cloneUrl("bbbbppp.com")
                .id(2L)
                .gitHubOwner(gitHubOwner)
                .build();

        List<GitHubRepository> gitHubRepositories = List.of(gitHubRepositoryA, gitHubRepositoryB);

        when(client.getRepositories(any())).thenReturn(gitHubRepositories);

        MvcResult mvcResult = mockMvc.perform(get("/repositories/{owner}",  gitHubOwner.getLogin()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        List<Object> responseList = Arrays.asList(
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RepositoryResponseDto[].class));

        assertThat(responseList).isNotNull();
        assertThat(responseList).hasSize(2);
    }
}
