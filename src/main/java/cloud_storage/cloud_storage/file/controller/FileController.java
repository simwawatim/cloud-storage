package cloud_storage.cloud_storage.file.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cloud_storage.cloud_storage.file.dto.UploadedFileDTO;
import cloud_storage.cloud_storage.file.model.UploadedFile;
import cloud_storage.cloud_storage.file.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // Upload a file (optional custom filename)
    @PostMapping("/upload/{folderId}")
    public UploadedFileDTO uploadFile(
            @PathVariable UUID folderId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "name", required = false) String customName
    ) throws IOException {
        UploadedFile uploadedFile = fileService.uploadFile(folderId, file, customName);
        return new UploadedFileDTO(uploadedFile); // return DTO
    }


    // List files in a folder
    @GetMapping("/folder/{folderId}")
    public List<UploadedFileDTO> getFilesByFolder(@PathVariable UUID folderId) {
        return fileService.getFilesByFolder(folderId).stream()
                .map(UploadedFileDTO::new)
                .collect(Collectors.toList());
    }

    // Download a file by fileId
    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID fileId) throws IOException {
        UploadedFile file = fileService.getFileById(fileId);

        // Read file bytes from disk
        Path filePath = Path.of(file.getFilePath());
        byte[] data = Files.readAllBytes(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(data);
    }
}
