package cloud_storage.cloud_storage.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cloud_storage.cloud_storage.users.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}
