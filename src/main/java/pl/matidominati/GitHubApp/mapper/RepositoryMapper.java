package pl.matidominati.GitHubApp.mapper;

import org.mapstruct.Mapping;
import pl.matidominati.GitHubApp.model.dto.RepoRequestDto;
import pl.matidominati.GitHubApp.model.dto.RepoResponseDto;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RepositoryMapper {

    @Mapping(source = "stargazersCount", target = "stars")
    @Mapping(source = "gitHubOwner.login", target = "ownerUsername")
    RepositoryPojo mapToPojo(GitHubRepository gitHubRepository);

    RepoResponseDto pojoToDto(RepositoryPojo repositoryPojo);
    RepoResponseDto mapToResponseDto(RepositoryDetails repositoryDetails);
    RepoRequestDto mapToRequest (RepositoryPojo repositoryPojo);
}
