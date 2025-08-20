package cloud_storage.cloud_storage.folders.model;
import java.time.LocalDateTime;
import java.util.UUID;

import cloud_storage.cloud_storage.folders.model.Folder;

public class FolderDTO {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String ownerUsername;

    public FolderDTO(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        this.createdAt = folder.getCreatedAt();
        this.updatedAt = folder.getUpdatedAt();
        this.ownerUsername = folder.getOwner() != null ? folder.getOwner().getUsername() : null;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getOwnerUsername() { return ownerUsername; }
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }
}
