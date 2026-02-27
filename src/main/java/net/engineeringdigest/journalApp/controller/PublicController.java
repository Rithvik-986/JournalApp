package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-ckeck")
    public String heathCheck(){
        return "OK";
    }

    @PostMapping("create-user")
    public User createUser(@RequestBody User user){

        userService.saveNewEntry(user);
        return user;

    }

}
