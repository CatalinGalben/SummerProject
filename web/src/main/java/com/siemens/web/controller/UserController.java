package com.siemens.web.controller;


import com.siemens.core.model.User;
import com.siemens.core.service.UserServiceInterface;
import com.siemens.web.converter.UserConverter;
import com.siemens.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserServiceInterface.class);

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public UserDTO createUser(@RequestBody final UserDTO userAdded)
    {
        System.out.println("UserController - createUser - enter");

        log.trace("createUser --- method entered: userEntered={}", userAdded);
        User savedUser = userServiceInterface.createUser(
                userAdded.getFirstName(),
                userAdded.getLastName(),
                userAdded.getEmail(),
                userAdded.getUsername(),
                userAdded.getPassword(),
                userAdded.getType(),
                userAdded.getBalance(),
                userAdded.getDob()
        );

        log.trace("createUser --- method finishing: user={}", savedUser);
        return userConverter.convertModelToDto(savedUser);
    }

    @RequestMapping(value = "users/{key}", method = RequestMethod.PUT)
    public UserDTO setCash(@PathVariable final int key, @RequestBody final int value)
    {
        User updatedUser = userServiceInterface.setAmountOfCash(key, value);

        return userConverter.convertModelToDto(updatedUser);
    }
}
