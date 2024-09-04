package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
}
