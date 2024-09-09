package com.kumar.shopperstop.Security.jwt;

import com.kumar.shopperstop.Security.User.ShopUserDetails;
import com.kumar.shopperstop.Security.User.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ShopUserDetailsService shopUserDetailsService;



//    public JwtAuthFilter(JwtUtils jwtUtils, ShopUserDetailsService shopUserDetailsService) {
//        this.jwtUtils = jwtUtils;
//        this.shopUserDetailsService =shopUserDetailsService;
//    }


    private String parseJwt(HttpServletRequest request) {

        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {


            String jwt = parseJwt(request);

            try{
                if(StringUtils.hasText(jwt) && jwtUtils.validateJwtToken(jwt)){
                    String username=jwtUtils.getUsernameFromToken(jwt);

                    UserDetails userDetails=shopUserDetailsService.loadUserByUsername(username);

                    Authentication auth=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);


                }
            }
            catch(JwtException e){

                log.error("Error logging in: {}",e.getMessage());
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Error logging in , invalid or expired jwt , try logging in again: "+e.getMessage());
                return ;

            }
            catch(Exception e){
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(e.getMessage());
                return;
            }

            filterChain.doFilter(request,response);


    }
}
