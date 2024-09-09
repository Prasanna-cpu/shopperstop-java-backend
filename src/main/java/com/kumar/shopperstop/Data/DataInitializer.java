package com.kumar.shopperstop.Data;


import com.kumar.shopperstop.Model.Role.Role;
import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Repository.Role.RoleRepository;
import com.kumar.shopperstop.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void createDefaultUserIfNotExists(){
        Role userRole=roleRepository.findByName("ROLE_USER").get();

        for(int i=1;i<=5;i++){
            String defaultEmail="user"+i+"@gmail.com";

            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user=new User();

            user.setFirstName("The user");
            user.setLastName("User"+i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(userRole));
            user.setPassword(passwordEncoder.encode("12436"));
            log.info("Default user no {} created: {}", i,defaultEmail);
            userRepository.save(user);

        }
    }

    private void createDefaultAdminIfNotExists(){
        Role adminRole=roleRepository.findByName("ROLE_ADMIN").get();
        for(int i=1;i<=2;i++){
            String defaultEmail="admin"+i+"@gmail.com";

            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user=new User();

            user.setFirstName("The admin");
            user.setLastName("Admin"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("12436"));
            user.setRoles(Set.of(adminRole));
            log.info("Default admin no {} created: {}", i,defaultEmail);
            userRepository.save(user);

        }
    }


    private void createDefaultRoleIfNotExists(Set<String> roles){
        roles.stream()
                .filter(role->roleRepository.findByName(role).isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
    }




    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles=Set.of("ROLE_ADMIN","ROLE_CUSTOMER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
