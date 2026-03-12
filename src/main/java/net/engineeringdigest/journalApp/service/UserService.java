package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder passwordEncode = new BCryptPasswordEncoder();

    public void saveUser(User entry){
        userRepository.save(entry);
    }

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncode.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            logger.error("Error Occured",e);
            logger.warn("awubgroaw");
            logger.info("awubgroaw");
            logger.trace("awubgroaw");
            logger.debug("awubgroaw");
            return false;
        }

    }

    public boolean saveAdmin(User user){
        try{
            user.setPassword(passwordEncode.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void deleteByUserName(String username){
        userRepository.deleteByUserName(username);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

}
