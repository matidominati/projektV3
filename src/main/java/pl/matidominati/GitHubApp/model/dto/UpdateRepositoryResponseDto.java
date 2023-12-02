package pl.matidominati.GitHubApp.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
//@NoArgsConstructor
public class UpdateRepositoryResponseDto extends RepositoryResponseDto {
    private boolean fullNameChanged;
    private boolean descriptionChanged;
    private boolean cloneUrlChanged;
    private boolean starsChanged;
    private boolean createdAtChanged;

    public UpdateRepositoryResponseDto(String fullName, String description, String cloneUrl, int stars, LocalDateTime createdAt,
                                   boolean fullNameChanged, boolean descriptionChanged, boolean cloneUrlChanged,
                                   boolean starsChanged, boolean createdAtChanged) {
        super(fullName, description, cloneUrl, stars, createdAt);
        this.fullNameChanged = fullNameChanged;
        this.descriptionChanged = descriptionChanged;
        this.cloneUrlChanged = cloneUrlChanged;
        this.starsChanged = starsChanged;
        this.createdAtChanged = createdAtChanged;
    }
}
