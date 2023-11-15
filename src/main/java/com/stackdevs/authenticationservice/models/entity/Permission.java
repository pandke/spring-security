package com.stackdevs.authenticationservice.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="permissions")
@Data
@NoArgsConstructor
public class Permission implements Serializable {
    @Serial
    private static final long serialVersionUID = 1000L;
    @Id
    @Column(name = "permission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;
    @Column(name = "name", unique = true)
    private String name;

    public Permission(String name) {
        this.name = name;
    }
}
