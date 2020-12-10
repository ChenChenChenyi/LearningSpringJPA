package com.example.jpademo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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
    public Iterable<User> getAllUser(){
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
        return userRepository.findAll(PageRequest.of(1,2,Sort.by(Sort.Direction.ASC,"Id")));
    }

    @PostMapping(path = "/find-by-name")
    @ResponseBody
    public User findByName(@RequestParam("name") String name){
        if(userRepository.findByName(name).isPresent()){
            return userRepository.findByName(name).get();
        }
        return null;
    }


}
