package com.kumar.shopperstop.Service.User;

import com.kumar.shopperstop.Exceptions.ExistingUserException;
import com.kumar.shopperstop.Exceptions.UserNotFoundException;
import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Request.CreateUserRequest;
import com.kumar.shopperstop.Request.UpdateUserRequest;

public interface UserService {

    User getUserById(Long userId) throws UserNotFoundException;

    User createUser(CreateUserRequest request) throws ExistingUserException;

    User updateUser(Long id, UpdateUserRequest request) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;


}
