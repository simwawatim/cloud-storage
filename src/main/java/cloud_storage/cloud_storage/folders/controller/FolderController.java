package cloud_storage.cloud_storage.folders.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud_storage.cloud_storage.folders.model.Folder;
import cloud_storage.cloud_storage.folders.service.FolderService;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    public Folder createFolder(@RequestBody Folder folder) {
        folder.setCreatedAt(LocalDateTime.now());
        folder.setUpdatedAt(LocalDateTime.now());
        return folderService.createFolder(folder);
    }

    @GetMapping
    public List<Folder> getAllFolders() {
        return folderService.getAllFolders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Folder> getFolder(@PathVariable UUID id) {
        Folder folder = folderService.getFolder(id);
        if (folder == null) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(folder);
    }
}
