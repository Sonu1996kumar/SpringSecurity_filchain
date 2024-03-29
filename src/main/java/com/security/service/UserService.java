package com.security.service;

import com.security.dto.PropertyUserDto;
import com.security.entity.PropertyUser;
import com.security.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private PropertyUserRepository propertyUserRepository;

    public UserService(PropertyUserRepository propertyUserRepository) {
        this.propertyUserRepository = propertyUserRepository;
    }

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
}
