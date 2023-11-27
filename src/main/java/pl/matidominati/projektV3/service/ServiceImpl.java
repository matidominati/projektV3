package pl.matidominati.projektV3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matidominati.projektV3.model.GithubRepo;

@Service
@RequiredArgsConstructor
public class ServiceImpl {

 private final GithubFeignClient githubFeignClient;
 public GithubRepo getGithubDetails(String owner, String repositoryName){
  return githubFeignClient.getRepositoryDetails(owner, repositoryName);

 }
}
