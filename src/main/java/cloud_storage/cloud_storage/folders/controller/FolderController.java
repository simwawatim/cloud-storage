package cloud_storage.cloud_storage.folders.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud_storage.cloud_storage.folders.model.Folder;
import cloud_storage.cloud_storage.folders.service.FolderService;
import cloud_storage.cloud_storage.users.model.AppUser;
import cloud_storage.cloud_storage.users.repository.AppUserRepository;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;
    private final AppUserRepository userRepository;

    public FolderController(FolderService folderService, AppUserRepository userRepository) {
        this.folderService = folderService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Folder createFolder(@RequestBody Folder folder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        AppUser loggedInUser = userRepository.findByUsername(username);

        folder.setOwner(loggedInUser);
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
