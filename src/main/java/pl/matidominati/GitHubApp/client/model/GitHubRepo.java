package pl.matidominati.GitHubApp.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepo {
    private Long id;
    private String name;
    private String full_name;
    private String language;
    private String visibility;
    private String default_branch;
    private String html_url;
    private String description;
    private int watchers;
    private boolean has_downloads;
    private boolean archived;
    private boolean disabled;
    private Owner owner;
}
