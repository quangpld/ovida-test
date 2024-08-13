package io.ovida.test.service;

import io.ovida.test.persistence.dao.PermissionRepository;
import io.ovida.test.persistence.dao.RoleRepository;
import io.ovida.test.persistence.model.Permission;
import io.ovida.test.persistence.model.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.ovida.test.constant.AppConstants.PERMISSION_NOT_FOUND_MESSAGE;
import static io.ovida.test.constant.AppConstants.ROLE_ADMIN;
import static io.ovida.test.constant.AppConstants.ROLE_NOT_FOUND_MESSAGE;

@Service
@Transactional
public class RolePermissionService implements IRolePermissionService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @PreAuthorize("hasAuthority('GRANT_PERMISSION')")
    public void grantPermission(Long roleId, Long permissionId) {
        final Optional<Role> foundRole = roleRepository.findById(roleId);
        if (!foundRole.isPresent()) {
            throw new EntityNotFoundException(ROLE_NOT_FOUND_MESSAGE);
        }

        final Optional<Permission> foundPermission = permissionRepository.findById(permissionId);
        if (!foundPermission.isPresent()) {
            throw new EntityNotFoundException(PERMISSION_NOT_FOUND_MESSAGE);
        }

        final Role role = foundRole.get();
        final Permission newPermission = foundPermission.get();
        if (!role.getPermissions().contains(newPermission)) {
            final List<Permission> updatedPermissionsList = new ArrayList<>(role.getPermissions());
            updatedPermissionsList.add(newPermission);
            role.setPermissions(updatedPermissionsList);

            roleRepository.save(role);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('REVOKE_PERMISSION')")
    public void revokePermission(Long roleId, Long permissionId) {
        final Optional<Role> foundRole = roleRepository.findById(roleId);
        if (!foundRole.isPresent()) {
            throw new EntityNotFoundException(ROLE_NOT_FOUND_MESSAGE);
        }

        final Role role = foundRole.get();
        if (ROLE_ADMIN.equals(role.getName())) {
            // Prevent revoking permission from ADMIN role
            return;
        }

        final Optional<Permission> foundPermission = permissionRepository.findById(permissionId);
        if (!foundPermission.isPresent()) {
            throw new EntityNotFoundException(PERMISSION_NOT_FOUND_MESSAGE);
        }

        final Permission permissionToBeRemoved = foundPermission.get();
        if (role.getPermissions().contains(permissionToBeRemoved)) {
            final List<Permission> updatedPermissionsList = role.getPermissions().stream().filter(p -> !p.getId().equals(permissionId)).toList();
            role.setPermissions(new ArrayList<>(updatedPermissionsList));

            roleRepository.save(role);
        }
    }
}
