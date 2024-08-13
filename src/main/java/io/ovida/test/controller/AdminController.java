package io.ovida.test.controller;

import io.ovida.test.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    RolePermissionService rolePermissionService;

    public AdminController() {
        super();
    }

    @PostMapping("/grant-permission")
    public void grantPermission(@RequestParam Long roleId, @RequestParam Long permissionId) {
        rolePermissionService.grantPermission(roleId, permissionId);
    }

    @DeleteMapping("/revoke-permission")
    public void revokePermission(@RequestParam Long roleId, @RequestParam Long permissionId) {
        rolePermissionService.revokePermission(roleId, permissionId);
    }
}
