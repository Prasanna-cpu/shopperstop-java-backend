package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.Request.LoginRequest;
import com.kumar.shopperstop.Response.ApiResponse;
import com.kumar.shopperstop.Response.JwtResponse;
import com.kumar.shopperstop.Security.User.ShopUserDetails;
import com.kumar.shopperstop.Security.jwt.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
        try{
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt=jwtUtils.generateJwtToken(authentication);
            ShopUserDetails userDetails=(ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse=new JwtResponse(userDetails.getId(),jwt);
            return ResponseEntity.ok(
                    new ApiResponse("Login sucessfull",jwtResponse)
            );
        }
        catch (AuthenticationException e){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            new ApiResponse("Invalid Credentials",e.getMessage())
                    );

        }

    }


}
