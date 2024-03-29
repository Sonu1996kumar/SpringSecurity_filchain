package com.security.controller;

import com.security.dto.PropertyUserDto;
import com.security.entity.PropertyUser;
import com.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
   private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8080/api/v1/addUser
    @PostMapping("/addUser")
    public ResponseEntity<String>addUser(@RequestBody PropertyUserDto dto) throws Exception {
        PropertyUser user = userService.addUser(dto);
        if (user == null) {
            return new ResponseEntity<>("sign up failed ", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>("sign up successful ", HttpStatus.CREATED);
        }

    }
}
