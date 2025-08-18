package cloud_storage.cloud_storage.folders.service;

import cloud_storage.cloud_storage.folders.model.Folder;
import cloud_storage.cloud_storage.folders.repository.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FolderService {

    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public Folder createFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }

    public Folder getFolder(UUID id) {
        return folderRepository.findById(id).orElse(null);
    }
}
