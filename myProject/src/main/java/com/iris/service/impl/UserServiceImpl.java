package com.iris.service.impl;

/**
 * Created by lwrong on 2018/1/30.
 */
import com.iris.dao.IUserDao;
import com.iris.model.User;
import com.iris.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Override
    public User selectUser(long userId) {
        return this.userDao.selectUser(userId);
    }

}
