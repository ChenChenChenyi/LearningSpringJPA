package com.example.jpademo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.tomcat.jni.Time.sleep;

@Controller
@RequestMapping(path = "/demo")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @GetMapping(path = "/add")
    @Transactional
    @ResponseBody
    public void addNewUser(@RequestParam String name, @RequestParam String email,@RequestParam Long psId){
        User n = new User();
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(psId).get();
        n.setName(name);
        n.setEmail(email);
        n.setPs(parkingSpace);
        //parkingSpaceRepository.save(parkingSpace);
        userRepository.save(n);
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<User> getAllUser() throws InterruptedException {
        System.out.println(9/4);
        return userRepository.findAll();
    }

    @GetMapping(path = "/sort")
    @ResponseBody
    public Iterable<User> getAllUsersWithSort(){
        //return userRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"name")));
        return userRepository.findAll(Sort.by(Sort.Direction.ASC,"Id"));
    }

    @GetMapping(path = "/page")
    @ResponseBody
    public Page<User> getUserByPage(){
        //return userRepository.findAll(PageRequest.of(1,2,Sort.by(Sort.Direction.ASC,"Id")));
        return userRepository.findAll(PageRequest.of(5,2,Sort.by(Sort.Direction.ASC,"Id")));
    }

    @PostMapping(path = "/find-by-name")
    @ResponseBody
    public User findByName(@RequestParam("name") String name) throws InterruptedException {
        if(userRepository.findByName(name).isPresent()){
            TimeUnit.SECONDS.sleep(5);
            return userRepository.findByName(name).get();
        }
        return null;
    }

    /**
    * @Description: 使用QueryByExampleExcutor
    * @Author: chenyi
    * @Date: 2020/12/10
    */
    @PostMapping(path = "/find-by-first-name")
    @ResponseBody
    public List<User> findByFirstName(@RequestParam("firstName") String firstName){
        User user = new User();
        user.setName(firstName);
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith()) //姓名采用“开始匹配”的方式查询
                .withIgnorePaths("focus");
        //创建实例
        Example<User> ex = Example.of(user, matcher);
        return userRepository.findAll(ex);
    }


}
