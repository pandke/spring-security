package com.stackdevs.authenticationservice.respository;

import com.stackdevs.authenticationservice.models.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
