package com.security.controller;

import com.security.dto.JwtResponse;
import com.security.dto.LoginDto;
import com.security.dto.PropertyUserDto;
import com.security.entity.PropertyUser;
import com.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
   private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8080/api/v1/addUser
    //Sign Up Feature
    @PostMapping("/addUser")
    public ResponseEntity<String>addUser(@RequestBody PropertyUserDto dto) throws Exception {
        PropertyUser user = userService.addUser(dto);
        if (user == null) {
            return new ResponseEntity<>("sign up failed ", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>("sign up successful ", HttpStatus.CREATED);
        }

    }

    //Sign In Feature without jwt token
//    @PostMapping("/login")
//    public ResponseEntity<String>login(@RequestBody LoginDto logindto) throws Exception {
//        boolean status = userService.verifyLogin(logindto);
//        if (!status) {
//            return new ResponseEntity<>("sign in failed Due to invalid Credential ", HttpStatus.UNAUTHORIZED);
//        }else{
//            return new ResponseEntity<>("sign in successful ", HttpStatus.OK );
//        }
//        System.out.println(logindto.getUserName());
//        System.out.println(logindto.getPassword());
//        return new ResponseEntity<>("sign in successful ", HttpStatus.OK);

    //}

    //signed in feature with jwt token

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginDto logindto) throws Exception {
        String jwtToken = userService.verifyLogin(logindto);

        if (jwtToken!=null) {
            JwtResponse jwtResponse =new JwtResponse();
            jwtResponse.setToken(jwtToken);

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("sign in failed due to Invalid Credential ", HttpStatus.UNAUTHORIZED);

        }
    }
    @GetMapping("/profile")//@AuthenticationPrincipal current login user ka detailed dega
    public PropertyUser getCurrentProfile(@AuthenticationPrincipal PropertyUser propertyUser){
        return propertyUser;
    }
}
