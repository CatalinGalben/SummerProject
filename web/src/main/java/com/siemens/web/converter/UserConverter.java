package com.siemens.web.converter;

import com.siemens.core.model.User;
import com.siemens.web.dto.UserDTO;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends AbstractConverterBaseEntity<User, UserDTO> {

    @Override
    public User convertDtoToModel(UserDTO userDTO){
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .type(userDTO.getType())
                .balance(userDTO.getBalance())
                .dob(userDTO.getDob())
                .build();
        user.setId(userDTO.getId());
        return user;
    }

    @Override
    public UserDTO convertModelToDto(User user) {
        UserDTO userDTO = UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .type(user.getType())
                .balance(user.getBalance())
                .dob(user.getDob())
                .build();
        userDTO.setId(user.getId());
        return userDTO;
    }
}
