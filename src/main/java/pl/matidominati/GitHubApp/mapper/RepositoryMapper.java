package pl.matidominati.GitHubApp.mapper;

import org.mapstruct.Mapping;
import pl.matidominati.GitHubApp.model.dto.RepositoryResponseDto;
import pl.matidominati.GitHubApp.model.dto.UpdateRepositoryResponseDto;
import pl.matidominati.GitHubApp.model.entity.RepositoryDetails;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.matidominati.GitHubApp.client.model.GitHubRepository;
import pl.matidominati.GitHubApp.model.pojo.RepositoryPojo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RepositoryMapper {

    @Mapping(source = "stargazersCount", target = "stars")
    @Mapping(source = "gitHubOwner.login", target = "ownerUsername")
    RepositoryPojo mapGitHubRepositoryToPojo(GitHubRepository gitHubRepository);

    RepositoryResponseDto mapPojoToDto(RepositoryPojo repositoryPojo);
    RepositoryResponseDto mapRepositoryDetailsToResponseDto(RepositoryDetails repositoryDetails);
    RepositoryPojo mapRepositoryDetailsToPojo(RepositoryDetails repositoryDetails);
    RepositoryDetails mapPojoToRepositoryDetails(RepositoryPojo repositoryPojo);
    UpdateRepositoryResponseDto mapPojoToUpdateRepositoryResponseDto(RepositoryPojo repositoryPojo);
}
