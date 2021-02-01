package com.example.jpademo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: 陈义
 * @Date: 2021/2/1 19:56
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByName() {
        Optional<User> user = userRepository.findByName("chenyi");
        if(user.isPresent()){
            System.out.println(user.get());
        }else{
            System.out.println("chenyi"+ "用户不存在！");
        }
    }
}