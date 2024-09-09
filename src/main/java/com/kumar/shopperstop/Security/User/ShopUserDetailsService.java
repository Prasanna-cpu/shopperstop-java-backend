package com.kumar.shopperstop.Security.User;

import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class ShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));

        UserDetails userDetails=ShopUserDetails.buildUserDetails(user);

        log.info("User loaded: {}", username);

        return userDetails;
    }
}
