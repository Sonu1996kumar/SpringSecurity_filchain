package com.security.service;

import com.security.dto.LoginDto;
import com.security.dto.PropertyUserDto;
import com.security.entity.PropertyUser;
import com.security.repository.PropertyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private PropertyUserRepository propertyUserRepository;

//    public UserService(PropertyUserRepository propertyUserRepository) { hatana pada
//        this.propertyUserRepository = propertyUserRepository;
//    }


    private JwtService jwtService;

    public UserService(PropertyUserRepository propertyUserRepository, JwtService jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService = jwtService;
    }

    //for SignUp
    public PropertyUser addUser(PropertyUserDto dto) {
        PropertyUser user = new PropertyUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUserName(dto.getUserName());
        user.setUserRole(dto.getUserRole());
        user.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));
        propertyUserRepository.save(user);
        return user;
    }


    //for SignIn without jwt
//    public boolean verifyLogin(LoginDto logindto) {
//        Optional<PropertyUser> opUser = propertyUserRepository.findByUserName(logindto.getUserName());
//        PropertyUser user = opUser.get();
//        if(user!=null && BCrypt.checkpw(logindto.getPassword(), user.getPassword())) {
//            return true;
//        }
//        return false;
//
//    }
    //by sir
//    if(opUser.isPresent()) {
//        PropertyUser user=opUser.get();
//        return BCrypt.checkpw(logindto.getPassword(), user.getPassword());
//    }


    //for SignIn with jwt
    public String verifyLogin(LoginDto logindto) {
        Optional<PropertyUser> opUser = propertyUserRepository.findByUserName(logindto.getUserName());
        if(opUser.isPresent()) {
            PropertyUser user=opUser.get();
            if( BCrypt.checkpw(logindto.getPassword(), user.getPassword())){
                return jwtService.generateToken(user);
            }
        }
                return null;
    }
}
