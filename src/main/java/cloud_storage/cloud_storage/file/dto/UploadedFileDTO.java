package cloud_storage.cloud_storage.file.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import cloud_storage.cloud_storage.file.model.UploadedFile;

public class UploadedFileDTO {
    private UUID id;
    private String filename;
    private String fileType;
    private long size;
    private String filePath;
    private UUID folderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UploadedFileDTO(UploadedFile file) {
        this.id = file.getId();
        this.filename = file.getFilename();
        this.fileType = file.getFileType();
        this.size = file.getSize();
        this.filePath = file.getFilePath();
        this.folderId = file.getFolder() != null ? file.getFolder().getId() : null;
        this.createdAt = file.getCreatedAt();
        this.updatedAt = file.getUpdatedAt();
    }

    // Getters & setters
    public UUID getId() { return id; }
    public String getFilename() { return filename; }
    public String getFileType() { return fileType; }
    public long getSize() { return size; }
    public String getFilePath() { return filePath; }
    public UUID getFolderId() { return folderId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
