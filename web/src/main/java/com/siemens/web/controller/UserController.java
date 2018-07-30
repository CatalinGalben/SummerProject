package com.siemens.web.controller;


import com.siemens.core.model.User;
import com.siemens.core.service.CurrencyServiceInterface;
import com.siemens.core.service.UserServiceInterface;
import com.siemens.web.converter.UserConverter;
import com.siemens.web.dto.PlainPriceWrapper;
import com.siemens.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private CurrencyServiceInterface currencyServiceInterface;

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public UserDTO createUser(@RequestBody final UserDTO userAdded)
    {
        System.out.println("UserController - createUser - enter");

        log.trace("createUser --- method entered: userEntered={}", userAdded);

        log.trace(userAdded.getDob());

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


    @RequestMapping(value = "/users/{username}/{password}", method = RequestMethod.GET)
    public UserDTO getOneUser(@PathVariable final String username, @PathVariable final String password)
    {
        log.trace("Login method entered! "+ username + password);
        User possibleLoggedUser = userServiceInterface.login(username, password);
//        currencyServiceInterface.addCurrency();
        log.trace(possibleLoggedUser+" Returned from backend");
        return userConverter.convertModelToDto(possibleLoggedUser);
    }


    @RequestMapping(value = "/users/balance/{key}", method = RequestMethod.POST)
    public UserDTO setCash(@PathVariable final Integer key,
                           @RequestBody final PlainPriceWrapper plainPriceWrapper)
    {
        Double newBalanceValue = plainPriceWrapper.getNewBalanceValue();
        User updatedUser = userServiceInterface.setAmountOfCash(key, newBalanceValue);

        return userConverter.convertModelToDto(updatedUser);
    }
    @RequestMapping(value = "/users/{key}", method = RequestMethod.GET)
    public UserDTO findById(@PathVariable final int key)
    {
        log.trace("requested single user !");
        return userConverter.convertModelToDto(userServiceInterface.findById(key));
    }
    @RequestMapping(value = "/users/dividend/{symbol}/{userKey}", method = RequestMethod.POST)
    public UserDTO addDividend(
            @PathVariable final String symbol,
            @PathVariable final Integer userKey,
            @RequestBody final Double earnedMoney)
    {
        log.trace("User requested dividend! -- Value of balance changed");
        return userConverter.convertModelToDto(userServiceInterface.addDividend(symbol,earnedMoney,userKey));
    }
}
