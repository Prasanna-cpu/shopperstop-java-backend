package com.kumar.shopperstop.Data;


import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    private void createDefaultUserIfNotExists(){
        for(int i=1;i<=5;i++){
            String defaultEmail="user"+i+"@gmail.com";

            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }

            User user=new User();

            user.setFirstName("The user");
            user.setLastName("User"+i);
            user.setEmail(defaultEmail);
            user.setPassword("123445");
            log.info("Default user no {} created: {}", i,defaultEmail);
            userRepository.save(user);

        }
    }



    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
