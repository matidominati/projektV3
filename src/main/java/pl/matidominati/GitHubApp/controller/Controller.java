package pl.matidominati.GitHubApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.matidominati.GitHubApp.client.model.GitHubRepo;
import pl.matidominati.GitHubApp.service.ServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/github-details")
@RequiredArgsConstructor
public class Controller {

    private final ServiceImpl serviceImpl;

    @Operation(summary = "Get repository details", tags = "GitHubApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GitHubRepo.class))}),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{owner}/{repo}")
    public GitHubRepo getRepositoryDetails(@PathVariable String owner, @PathVariable String repo) {
        return serviceImpl.getRepositoryDetails(owner, repo);
    }

    @Operation(summary = "Get user repositories", tags = "GitHubApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GitHubRepo.class)))}),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{owner}")
    public List<GitHubRepo> getRepositories(@PathVariable String owner) {
        return serviceImpl.getRepository(owner);
    }
}
