package pl.matidominati.GitHubApp.mapper;

import org.mapstruct.Mapping;
import pl.matidominati.GitHubApp.model.dto.RepoRequestDto;
import pl.matidominati.GitHubApp.model.dto.RepoResponseDto;
import pl.matidominati.GitHubApp.model.entity.Repo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RepoMapper {

    @Mapping(source = "stargazersCount", target = "stars")
    RepoResponseDto mapToDto (GitHubRepository gitHubRepository);

    @Mapping(source = "repoName", target = "fullName")
    RepoResponseDto mapToDto (Repo repo);
    RepoRequestDto mapToRequest (RepoResponseDto repoResponseDto);
}
