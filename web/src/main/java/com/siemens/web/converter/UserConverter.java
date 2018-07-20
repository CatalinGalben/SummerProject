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
                .password(userDTO.getPassword())
                .type(userDTO.getType())
                .dob(DateTime.parse(userDTO.getDob()))
                .balance(userDTO.getBalance())
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
                .password(user.getPassword())
                .type(user.getType())
                .dob(user.getDob().toString())
                .balance(user.getBalance())
                .build();
        userDTO.setId(user.getId());
        return userDTO;
    }
}
