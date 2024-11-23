package cz.erms.backend.ejb.repository;

import cz.erms.backend.ejb.entity.File;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    List<File> findAll();

    Optional<File> findById(UUID uuid);
}
