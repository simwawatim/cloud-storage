package cloud_storage.cloud_storage.folders.repository;

import cloud_storage.cloud_storage.folders.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {

}
