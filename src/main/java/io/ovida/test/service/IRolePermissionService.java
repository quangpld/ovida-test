package io.ovida.test.service;

public interface IRolePermissionService {

    void grantPermission(Long roleId, Long permissionId);

    void revokePermission(Long roleId, Long permissionId);
}
