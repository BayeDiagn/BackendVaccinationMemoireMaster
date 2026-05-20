package com.gestionvaccination.auth_service1.mapper;

import com.gestionvaccination.auth_service1.dto.UserDto;
import com.gestionvaccination.auth_service1.entity.User;
import org.springframework.stereotype.Component;



@Component
public class UserMapper {
    
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());

        if(user.getLangueMaternelle()!=null){
            user.setLangueMaternelle(userDto.getLangueMaternelle());
        }else {
            user.setLangueMaternelle(null);
        }
        return user;
    }

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setLangueMaternelle(user.getLangueMaternelle());
        return userDto;
    }
}
