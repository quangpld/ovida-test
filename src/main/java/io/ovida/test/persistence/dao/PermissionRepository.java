package io.ovida.test.persistence.dao;

import io.ovida.test.persistence.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
