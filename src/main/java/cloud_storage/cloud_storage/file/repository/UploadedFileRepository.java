package cloud_storage.cloud_storage.file.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cloud_storage.cloud_storage.file.model.UploadedFile;

@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, UUID> {
    List<UploadedFile> findByFolderId(UUID folderId);
}