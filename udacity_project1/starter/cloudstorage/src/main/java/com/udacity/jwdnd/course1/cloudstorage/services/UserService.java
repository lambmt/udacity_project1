package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

public interface UserService {
    User getUser(String username);
    String signUpUser(User user);
}
