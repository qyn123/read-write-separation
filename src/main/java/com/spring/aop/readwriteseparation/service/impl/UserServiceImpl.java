package com.spring.aop.readwriteseparation.service.impl;

import com.spring.aop.readwriteseparation.annoation.Master;
import com.spring.aop.readwriteseparation.mapper.UserMapper;
import com.spring.aop.readwriteseparation.model.pojo.User;
import com.spring.aop.readwriteseparation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yn.qiao
 * @version 1.0
 * @create 2021-05-20 22:50
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Master
    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectById(id);
    }
}
