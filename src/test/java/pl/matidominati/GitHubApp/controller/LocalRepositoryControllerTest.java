package pl.matidominati.GitHubApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.matidominati.GitHubApp.client.GitHubClient;
import pl.matidominati.GitHubApp.client.model.GitHubOwner;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
import pl.matidominati.GitHubApp.repository.LocalRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LocalRepositoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LocalRepository localRepository;
    @MockBean
    private GitHubClient gitHubClient;

    @Test
    @Transactional
    void shouldGetLocalRepository() throws Exception {
        RepositoryDetails newRepositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("user")
                .build();
        localRepository.save(newRepositoryDetails);

        MvcResult mvcResult = mockMvc.perform(get("/local/repositories/{owner}/{repoName}", newRepositoryDetails.getOwnerUsername(), newRepositoryDetails.getName()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        RepositoryResponseDto responseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RepositoryResponseDto.class);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getFullName()).isEqualTo(newRepositoryDetails.getFullName());
        assertThat(responseDto.getDescription()).isEqualTo(newRepositoryDetails.getDescription());
        assertThat(responseDto.getCloneUrl()).isEqualTo(newRepositoryDetails.getCloneUrl());
        assertThat(responseDto.getCreatedAt()).isEqualTo(newRepositoryDetails.getCreatedAt());
        assertThat(responseDto.getStars()).isEqualTo(newRepositoryDetails.getStars());
    }

    @Test
    @Transactional
    void shouldSaveNewRepository() throws Exception {
        GitHubOwner gitHubOwner = new GitHubOwner(1L, "user");
        GitHubRepository gitHubRepository = GitHubRepository.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stargazersCount(5)
                .gitHubOwner(gitHubOwner)
                .build();

        when(gitHubClient.getRepositoryDetails(any(), any())).thenReturn(Optional.ofNullable(gitHubRepository));

        RepositoryDetails newRepositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername(gitHubOwner.getLogin())
                .build();

        MvcResult mvcResult = mockMvc.perform(post("/local/repositories/{owner}/{repoName}", newRepositoryDetails.getOwnerUsername(), newRepositoryDetails.getName()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        RepositoryResponseDto responseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RepositoryResponseDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getFullName()).isEqualTo(newRepositoryDetails.getFullName());
        assertThat(responseDto.getDescription()).isEqualTo(newRepositoryDetails.getDescription());
        assertThat(responseDto.getCloneUrl()).isEqualTo(newRepositoryDetails.getCloneUrl());
        assertThat(responseDto.getCreatedAt()).isEqualTo(newRepositoryDetails.getCreatedAt());
        assertThat(responseDto.getStars()).isEqualTo(newRepositoryDetails.getStars());
    }

    @Test
    @Transactional
    void shouldEditLocalRepository() throws Exception {
        RepositoryDetails originalRepositoryDetails = RepositoryDetails.builder()
                .name("aaaaaa")
                .fullName("bbbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("user")
                .build();
        localRepository.save(originalRepositoryDetails);

        RepositoryPojo updatedRepositoryDetails = RepositoryPojo.builder()
                .fullName("XXXXX")
                .description("DDDDD")
                .cloneUrl("YYY.com")
                .createdAt(LocalDateTime.of(2020, 11, 23, 10, 19, 22))
                .stars(100)
                .build();

        MvcResult mvcResult = mockMvc.perform(put("/local/repositories/{owner}/{repoName}", originalRepositoryDetails.getOwnerUsername(), originalRepositoryDetails.getName())
                        .content(objectMapper.writeValueAsString(updatedRepositoryDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        RepositoryResponseDto responseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RepositoryResponseDto.class);
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getFullName()).isEqualTo(updatedRepositoryDetails.getFullName());
        assertThat(responseDto.getDescription()).isEqualTo(updatedRepositoryDetails.getDescription());
        assertThat(responseDto.getCloneUrl()).isEqualTo(updatedRepositoryDetails.getCloneUrl());
        assertThat(responseDto.getCreatedAt()).isEqualTo(updatedRepositoryDetails.getCreatedAt());
        assertThat(responseDto.getStars()).isEqualTo(updatedRepositoryDetails.getStars());
    }

    @Test
    @Transactional
    void shouldDeleteRepository() throws Exception {
        RepositoryDetails newRepositoryDetails = RepositoryDetails.builder()
                .name("aaaaa")
                .fullName("bbbbb")
                .description("ccccc")
                .cloneUrl("zzz.com")
                .createdAt(LocalDateTime.of(2022, 11, 23, 10, 19, 22))
                .stars(5)
                .ownerUsername("user")
                .build();
        localRepository.save(newRepositoryDetails);

        mockMvc.perform(delete("/local/repositories/{owner}/{repoName}", newRepositoryDetails.getOwnerUsername(), newRepositoryDetails.getName()))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
