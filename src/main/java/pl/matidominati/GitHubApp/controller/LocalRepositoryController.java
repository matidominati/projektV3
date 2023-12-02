package pl.matidominati.GitHubApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.matidominati.GitHubApp.exception.message.ErrorMessage;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;
import pl.matidominati.GitHubApp.service.LocalRepositoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/local/repositories")
public class LocalRepositoryController {
    private final LocalRepositoryService localService;

    @Operation(summary = "Get local repository", tags = "Local repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))})
    })
    @GetMapping("/{owner}/{repoName}")
    public RepositoryResponseDto getLocalRepository(@PathVariable String owner, @PathVariable String repoName) {
        return localService.getLocalRepositoryDetails(owner, repoName);
    }

    @Operation(summary = "Save new repository", tags = "Local repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "409", description = "Data already exists",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))})
    })
    @PostMapping("/{owner}/{repoName}")
    public RepositoryResponseDto saveRepository(@PathVariable String owner, @PathVariable String repoName) {
        return localService.saveRepositoryDetails(owner, repoName);
    }
    @Operation(summary = "Edit local repository", tags = "Local repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RepositoryResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))})
    })
    @PutMapping("/{owner}/{repoName}")
    public RepositoryResponseDto editLocalRepository(@PathVariable String owner, @PathVariable String repoName,
                                                     @RequestBody RepositoryPojo updatedDetails) {
        return localService.editLocalRepositoryDetails(owner, repoName, updatedDetails);
    }

    @Operation(summary = "Delete local repository from DB", tags = "Local repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Data not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))})
    })
    @DeleteMapping("/{owner}/{repoName}")
    public void deleteRepository(@PathVariable String owner, @PathVariable String repoName){
        localService.deleteLocalRepositoryDetails(owner, repoName);
    }
}
