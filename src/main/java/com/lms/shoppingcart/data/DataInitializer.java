package com.lms.shoppingcart.data;

import com.lms.shoppingcart.role.Role;
import com.lms.shoppingcart.user.User;
import com.lms.shoppingcart.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USERS");
        createDefaultRoleIfNotExits(defaultRoles);
        createDefaultUserIfNotExits();
        createDefaultAdminIfNotExits();
    }

    private void createDefaultUserIfNotExits() {
        Role userRole = roleRepository.findByRoleName("ROLE_USERS")
                .orElseThrow(() -> new RuntimeException("ROLE_USERS not found"));
        ;
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of((Role) userRole));
            userRepository.save(user);
            System.out.println("Default Vet user " + i + " Created Successfully.");
        }
    }

    private void createDefaultAdminIfNotExits() {
        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_USERS not found"));
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of((Role) adminRole));
            userRepository.save(user);
            System.out.println("Default admin user " + i + " Created Successfully.");
        }
    }

    private void createDefaultRoleIfNotExits(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByRoleName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }

}
