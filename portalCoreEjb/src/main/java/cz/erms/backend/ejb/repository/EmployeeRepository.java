package cz.erms.backend.ejb.repository;

import cz.erms.backend.ejb.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findAll();

    Optional<Employee> findById(UUID uuid);
}
