package com.stackdevs.authenticationservice.respository;

import com.stackdevs.authenticationservice.models.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}