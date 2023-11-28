package pl.matidominati.GitHubApp.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private Long id;
    private String login;
    private String type;
    private String html_url;
}
