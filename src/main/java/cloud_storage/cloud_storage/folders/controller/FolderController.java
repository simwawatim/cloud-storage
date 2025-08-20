package cloud_storage.cloud_storage.folders.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import cloud_storage.cloud_storage.folders.model.FolderDTO;
import cloud_storage.cloud_storage.folders.service.FolderService;
import cloud_storage.cloud_storage.users.model.AppUser;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    public ResponseEntity<FolderDTO> createFolder(@RequestBody Folder folder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AppUser)) {
            System.out.println("Unauthorized: No authenticated user found");
            return ResponseEntity.status(401).body(null);
        }

        AppUser loggedInUser = (AppUser) authentication.getPrincipal();
        System.out.println("Authenticated user: " + loggedInUser.getUsername());

        folder.setOwner(loggedInUser);
        folder.setCreatedAt(LocalDateTime.now());
        folder.setUpdatedAt(LocalDateTime.now());

        Folder savedFolder = folderService.createFolder(folder);

        return ResponseEntity.ok(new FolderDTO(savedFolder));
    }

    @GetMapping
    public ResponseEntity<List<FolderDTO>> getAllFolders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AppUser)) {
            System.out.println("Unauthorized: No authenticated user found");
            return ResponseEntity.status(401).body(null);
        }

        AppUser loggedInUser = (AppUser) authentication.getPrincipal();
        System.out.println("Authenticated user for GET /folders: " + loggedInUser.getUsername());

        List<FolderDTO> folders = folderService.getAllFolders()
                .stream()
                .map(FolderDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(folders);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FolderDTO> getFolder(@PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AppUser)) {
            System.out.println("Unauthorized: No authenticated user found");
            return ResponseEntity.status(401).body(null);
        }

        AppUser loggedInUser = (AppUser) authentication.getPrincipal();
        System.out.println("Authenticated user for GET /folders/{id}: " + loggedInUser.getUsername());

        Folder folder = folderService.getFolder(id);
        if (folder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new FolderDTO(folder));
    }
}
