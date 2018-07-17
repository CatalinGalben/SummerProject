package com.siemens.web.controller;


import com.siemens.core.model.User;
import com.siemens.core.service.UserServiceInterface;
import com.siemens.web.converter.UserConverter;
import com.siemens.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public UserDTO createUser(@RequestBody final UserDTO userDTO)
    {
        User savedUser = userServiceInterface.createUser(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getType(),
                userDTO.getBalance(),
                userDTO.getDob()
        );

        return userConverter.convertModelToDto(savedUser);
    }
}
