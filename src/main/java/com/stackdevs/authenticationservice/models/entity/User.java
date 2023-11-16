package com.stackdevs.authenticationservice.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseModel implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1000L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Long userId;
    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;
    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @Email(message = "Please use a valid email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "department_id")
    private long departmentId;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
    @JoinColumn(name="department_id", referencedColumnName = "department_id", insertable = false, updatable = false)
    private Department department;

    @Column(name = "mobile_number")
    @Size(min = 10, max=12)
    @JsonProperty("mobile_number")
    private String phoneNumber;

    @Column(name = "user_enabled", length = 1)
    @JsonProperty("is_enabled")
    private byte user_enabled;

    @JsonProperty("updated_by")
    @Column(name = "last_password_change_date")
    private ZonedDateTime lastPasswordChangeDate;       // for tracking password expiry

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles()
                .parallelStream()
                .flatMap(r -> r.getPermissions().stream()) // Use stream here to avoid nested parallel streams
                .map(Permission::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user_enabled == (byte)1;
    }
}
