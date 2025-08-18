package cloud_storage.cloud_storage.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cloud_storage.cloud_storage.file.model.UploadedFile;
import cloud_storage.cloud_storage.file.repository.UploadedFileRepository;
import cloud_storage.cloud_storage.folders.model.Folder;
import cloud_storage.cloud_storage.folders.repository.FolderRepository;

@Service
public class FileService {

    private final UploadedFileRepository fileRepository;
    private final FolderRepository folderRepository;

    public FileService(UploadedFileRepository fileRepository, FolderRepository folderRepository) {
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
    }

    public UploadedFile uploadFile(UUID folderId, MultipartFile file) throws IOException {
        // Get folder
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        // Ensure folder exists on disk
        String folderPath = "uploads/" + folder.getId();
        Path folderDir = Paths.get(folderPath);
        if (!Files.exists(folderDir)) {
            Files.createDirectories(folderDir);
        }

        // Save file on disk
        String filePath = folderPath + "/" + file.getOriginalFilename();
        Path destination = Paths.get(filePath);
        file.transferTo(destination);

        // Save metadata in DB
        UploadedFile uploadedFile = new UploadedFile(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                filePath,
                folder
        );
        uploadedFile.setCreatedAt(LocalDateTime.now());
        uploadedFile.setUpdatedAt(LocalDateTime.now());

        return fileRepository.save(uploadedFile);
    }

    public List<UploadedFile> getFilesByFolder(UUID folderId) {
        return fileRepository.findByFolderId(folderId);
    }

    public UploadedFile getFileById(UUID fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}
