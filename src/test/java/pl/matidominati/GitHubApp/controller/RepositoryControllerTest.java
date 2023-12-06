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
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
import pl.matidominati.GitHubApp.repository.LocalRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.matidominati.GitHubApp.controller.DataFactory.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RepositoryControllerTest {
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
        RepositoryDetails newRepositoryDetails = createRepositoryDetails();
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
        GitHubRepository gitHubRepository = createGitHubRepository();

        when(gitHubClient.getRepositoryDetails(any(), any())).thenReturn(Optional.ofNullable(gitHubRepository));

        RepositoryDetails newRepositoryDetails = createRepositoryDetails();

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
        RepositoryDetails originalRepositoryDetails = createRepositoryDetails();
        localRepository.save(originalRepositoryDetails);
        RepositoryPojo updatedRepositoryDetails = createUpdatedRepositoryDetails();

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
        RepositoryDetails newRepositoryDetails = createRepositoryDetails();
        localRepository.save(newRepositoryDetails);

        mockMvc.perform(delete("/local/repositories/{owner}/{repoName}", newRepositoryDetails.getOwnerUsername(), newRepositoryDetails.getName()))
                .andDo(print())
                .andExpect(status().is(200));
    }
}