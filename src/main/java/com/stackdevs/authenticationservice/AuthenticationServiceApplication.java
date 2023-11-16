package com.stackdevs.authenticationservice;

import com.stackdevs.authenticationservice.models.entity.Permission;
import com.stackdevs.authenticationservice.models.entity.Role;
import com.stackdevs.authenticationservice.models.entity.User;
import com.stackdevs.authenticationservice.respository.PermissionRepository;
import com.stackdevs.authenticationservice.respository.RoleRepository;
import com.stackdevs.authenticationservice.respository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class AuthenticationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(UserRepository userRepository,
										RoleRepository roleRepository,
										PermissionRepository permissionRepository,
										PasswordEncoder passwordEncoder) {
		return args -> {
			Permission viewUsers =new  Permission("view_users");
			Permission createUser = new Permission("create_users");
			Permission disableUsers  = new Permission("disable_users");

			permissionRepository.saveAll(Set.of(viewUsers, createUser, disableUsers));


			Role roleAdmin = new Role("ADMIN");
			roleAdmin.setPermissions(Set.of(viewUsers,createUser, disableUsers));

			Role roleUser = new Role("USER");
			roleUser.setPermissions(Set.of(viewUsers));

			Set<Role> roles = Set.of(roleAdmin, roleUser);

			if (roleRepository.count() != 2) {
				roleRepository.saveAll(roles);
			}

			User admin = new User("benson","opisa","bensonopisa@gmail.com", passwordEncoder.encode("Data2020."), 1L, roles);
			userRepository.save(admin);
		};
	}
}
