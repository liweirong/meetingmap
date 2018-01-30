package com.iris.dao;
import com.iris.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by lwrong on 2018/1/30.
 */
public interface IUserDao {

    User selectUser(long id);

}