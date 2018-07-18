package com.siemens.web.controller;


import com.siemens.core.model.User;
import com.siemens.core.service.UserServiceInterface;
import com.siemens.web.converter.UserConverter;
import com.siemens.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                "user",
                userDTO.getBalance(),
                userDTO.getDob()
        );

        return userConverter.convertModelToDto(savedUser);
    }

    @RequestMapping(value = "users/{key}", method = RequestMethod.PUT)
    public UserDTO setCash(@PathVariable final int key, @RequestBody final int value)
    {
        User updatedUser = userServiceInterface.setAmountOfCash(key, value);

        return userConverter.convertModelToDto(updatedUser);
    }
}
