package pl.matidominati.GitHubApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.model.dto.UpdateRepositoryResponseDto;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
import pl.matidominati.GitHubApp.service.GitHubService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RepositoryController {

    private final GitHubService gitHubService;

    @Operation(summary = "Get GitHub repository details", tags = "GitHubApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GitHubRepository.class))}),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/repositories/{gitHubOwner}/{repoName}")
    public RepositoryResponseDto getClientRepositoryDetails(@PathVariable String gitHubOwner, @PathVariable String repoName) {
        return gitHubService.getClientRepositoryDetails(gitHubOwner, repoName);
    }

    @Operation(summary = "Get GitHub user repositories", tags = "GitHubApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GitHubRepository.class)))}),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/repositories/{gitHubOwner}")
    public List<RepositoryResponseDto> getClientRepositories(@PathVariable String gitHubOwner) {
        return gitHubService.getClientRepositories(gitHubOwner);
    }

    @Operation(summary = "Get local repository", tags = "GitHubApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/local/repositories/{owner}/{repoName}")
    public RepositoryResponseDto getLocalRepository(@PathVariable String owner, @PathVariable String repoName) {
        return gitHubService.getLocalRepositoryDetails(owner, repoName);
    }

    @Operation(summary = "Save new repository", tags = "GitHubApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateRepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "409", description = "Data already exists",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/repositories/{owner}/{repoName}")
    public RepositoryResponseDto saveRepository(@PathVariable String owner, @PathVariable String repoName) {
        return gitHubService.saveRepositoryDetails(owner, repoName);
    }
    @Operation(summary = "Edit local repository", tags = "GitHubApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/repositories/{owner}/{repoName}")
    public RepositoryResponseDto editLocalRepository(@PathVariable String owner, @PathVariable String repoName,
                                                     @RequestBody RepositoryPojo updatedDetails) {
        return gitHubService.editLocalRepositoryDetails(owner, repoName, updatedDetails);
    }

    @Operation(summary = "Delete local repository from DB", tags = "GitHubApp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/repositories/{owner}/{repoName}")
    public void deleteRepository(@PathVariable String owner, @PathVariable String repoName){
        gitHubService.deleteLocalRepositoryDetails(owner, repoName);
    }
}
