package com.siemens.core.service;

import com.siemens.core.model.User;
import com.siemens.core.repository.UserRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserServiceInterface{
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            String type,
            Double income,
            DateTime dob){
        User user = User.builder()
                .firstName( firstName )
                .lastName( lastName )
                .email( email )
                .type( type )
                .username( username )
                .password( password )
                .dob( dob )
                .income( income )
                .build();
        return userRepository.save( user );
    }
    @Override
    public User findById(Integer key){
        return userRepository.getOne(key);
    }

}
