package com.siemens.core.service;

import com.siemens.core.model.User;
import org.joda.time.DateTime;

public interface UserServiceInterface {
    User createUser(
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            String type,
            Double income,
            DateTime dob);
    User findById(Integer key);


}
