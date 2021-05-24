package com.spring.aop.readwriteseparation.service;

import com.spring.aop.readwriteseparation.model.pojo.User;

/**
 * @author yn.qiao
 * @version 1.0
 * @create 2021-05-20 22:49
 **/
public interface UserService {

    int addUser(User user);

    User selectUserById(Integer id);
}
