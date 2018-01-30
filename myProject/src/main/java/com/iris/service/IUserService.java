package com.iris.service;

import com.iris.model.User;

/**
 * Created by lwrong on 2018/1/30.
 */
public interface IUserService {
    public User selectUser(long userId);
}
