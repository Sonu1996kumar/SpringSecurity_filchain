package com.security.config;

import com.security.entity.PropertyUser;
import com.security.repository.PropertyUserRepository;
import com.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
@Component
public class JWTRequestFilter extends OncePerRequestFilter {//incoming http request

    private JwtService jwtService;
    private PropertyUserRepository propertyUserRepository;

    public JWTRequestFilter(JwtService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String tokenHeader = request.getHeader("Authorization");
//        if (tokenHeader!= null && tokenHeader.startsWith("Bearer ")) {
//            String token = tokenHeader.substring(7);
//            String username = jwtService.getUsername(token);
//            Optional<PropertyUser> opUser = propertyUserRepository.findByUserName(username);
//            if(opUser.isPresent()) {
//                PropertyUser propertyUser= opUser.get();
//                //serve to understand who is the current user
//                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(propertyUser,null,new ArrayList<>());
//                //iske pass se hum username lenge kyunki role to define nhi kiya hai
//                //setting session(asnwb)
//                authentication.setDetails(new WebAuthenticationDetails(request));
//                //help in set the  session for the current user login
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            }
//        }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader!= null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            String username = jwtService.getUsername(token);
            Optional<PropertyUser> opUser = propertyUserRepository.findByUserName(username);
            if(opUser.isPresent()) {
                PropertyUser propertyUser= opUser.get();
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(propertyUser,null, Collections.singletonList(new SimpleGrantedAuthority(propertyUser.getUserRole())));
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request, response);//baki sab hamara filter chalne ke baad chalega
    }

}
