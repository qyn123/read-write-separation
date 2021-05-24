package com.spring.aop.readwriteseparation;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.spring.aop.readwriteseparation.model.pojo.User;
import com.spring.aop.readwriteseparation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReadWriteSeparationApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void testInsert() {
        userService.addUser(new User(20,"yn.q"));
    }


    @Test
    void testSelect(){
        User user = userService.selectUserById(13);
        System.out.println(user);
    }

}
