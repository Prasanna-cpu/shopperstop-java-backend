package com.kumar.shopperstop.Service.User;

import com.kumar.shopperstop.DTO.UserDTO;
import com.kumar.shopperstop.Exceptions.ExistingUserException;
import com.kumar.shopperstop.Exceptions.UserNotFoundException;
import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Repository.User.UserRepository;
import com.kumar.shopperstop.Request.CreateUserRequest;
import com.kumar.shopperstop.Request.UpdateUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional(rollbackOn =Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO mapToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        return user;
    }

    @Override
    public User createUser(CreateUserRequest request) throws ExistingUserException {
        User createdUser= Optional.of(request)
                .filter(user->!userRepository.existsByEmail(request.getEmail()))
                .map(req->{
                    User user=new User();
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    return userRepository.save(user);
                })
                .orElseThrow(()->new ExistingUserException("User with given email already exists"));
        return createdUser;
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) throws UserNotFoundException {
        User user=userRepository
                .findById(id)
                .map(existingUser->{
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(()->new UserNotFoundException("User not found"));
        return user;
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }

    @Override
    public User getAuthenticatedUser() throws UserNotFoundException {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found with given email: "+email));

        return user;

    }
}
