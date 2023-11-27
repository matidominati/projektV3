package pl.matidominati.GitHubApp.model;

import lombok.Data;

@Data
public class Owner {
    private Long id;
    private String login;
    private String type;
    private String html_url;
}
