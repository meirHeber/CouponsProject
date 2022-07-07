package com.meir.coupons.controller;

import com.meir.coupons.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserController usersController;

    @PostMapping
    public void createUser(@RequestBody UserEntity user) {
        usersController.createUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody UserEntity user) {
    }

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") long id) {
        return usersController.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        usersController.deleteUser(id);
    }

    @GetMapping("/byName")
    public List<UserEntity> findByName(@RequestParam("name") String name){
        return this.usersController.findByName(name);
    }

    @GetMapping("/byData")
    public List<UserEntity> getByData(@RequestParam("name") String name,
                                      @RequestParam("age") int age){
        return this.usersController.getByData(name, age);
    }
}
