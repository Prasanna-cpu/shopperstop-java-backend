package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.Exceptions.ExistingUserException;
import com.kumar.shopperstop.Exceptions.UserNotFoundException;
import com.kumar.shopperstop.Model.User.User;
import com.kumar.shopperstop.Repository.User.UserRepository;
import com.kumar.shopperstop.Request.CreateUserRequest;
import com.kumar.shopperstop.Request.UpdateUserRequest;
import com.kumar.shopperstop.Response.ApiResponse;
import com.kumar.shopperstop.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;


    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) throws UserNotFoundException {
        User user=userService.getUserById(id);

        try{
            return ResponseEntity.ok(
                    new ApiResponse(
                            "User retrieved",
                            user
                    )
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    "Operation failed",
                                    e.getMessage()
                            )
                    );
        }

    }


    @PostMapping("/user/add")
    public ResponseEntity<ApiResponse> createUser(
            @RequestBody CreateUserRequest request
    ) throws ExistingUserException {
        User user=userService.createUser(request);

        try{
            return ResponseEntity.ok(
                    new ApiResponse(
                            "User created",
                            user
                    )
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    "Operation failed",
                                    e.getMessage()
                            )
                    );
        }

    }


    @PutMapping("/user/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request,@PathVariable Long id) throws UserNotFoundException {

        User user=userService.updateUser(id,request);

        try{
            return ResponseEntity.ok(
                    new ApiResponse(
                            "User updated",
                            user
                    )
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    "Operation failed",
                                    e.getMessage()
                            )
                    );
        }

    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) throws UserNotFoundException {

        userService.deleteUser(id);

        try{
            return ResponseEntity.ok(
                    new ApiResponse(
                            "User deleted",
                            null
                    )
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ApiResponse(
                                    "Operation failed",
                                    e.getMessage()
                            )
                    );
        }

    }


























}
