//package pl.matidominati.GitHubApp.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import pl.matidominati.GitHubApp.client.model.RepositoryDetails;
//import pl.matidominati.GitHubApp.client.model.GitHubOwner;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setup() {
//        GitHubOwner gitHubOwner = GitHubOwner.builder()
//                .id(1L)
//                .login("owner123")
//                .html_url("xyz123")
//                .type("user")
//                .build();
//        RepositoryDetails gitHubRepo = RepositoryDetails.builder()
//                .default_branch("xxx")
//                .id(2L)
//                .name("repo")
//                .build();
//    }
//
//    @Test
//    void getRepositoryDetails_ValidOwnerAndRepo_ReturnsRepoDetails() throws Exception {
//
//        mockMvc.perform(get("/github-details/{gitHubOwner}/{repo}"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.login").value("owner123"))
//                .andExpect(jsonPath("$.name").value("repo"));
//
//
//    }
//
//}
