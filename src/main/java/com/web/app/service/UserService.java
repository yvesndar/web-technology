package com.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.web.app.model.NewUser;
import com.web.app.model.Roles;
import com.web.app.model.User;
import com.web.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MailService mailService;
    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    
    
    public NewUser createUser(NewUser newUser) {
    	System.out.println(newUser.getPassword()+" is it equal to "+newUser.getConfirm());
    	if(newUser.getPassword().equals(newUser.getConfirm())) {
    		User user = new User();
            Optional<User> byUsername = userRepository.findByUsername(newUser.getUsername());
            if (byUsername.isPresent()) {
            	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already registered. Please use different username.");
            }
            if(newUser.getRole()==null) {
            	newUser.setRole(Roles.USER);
            	user.setFirstname(newUser.getFirstname());
                user.setLastname(newUser.getLastname());
                user.setFirstname(newUser.getFirstname());
                user.setEmail(newUser.getEmail());
                user.setUsername(newUser.getUsername());
                user.setRole(newUser.getRole());
                String hashedPassword = bcrypt.encode(newUser.getPassword());
                System.out.println(user.getFirstname());
                user.setPassword(hashedPassword);
                userRepository.save(user);
                mailService.newAccountEmail(user);
            }
            user.setFirstname(newUser.getFirstname());
            user.setLastname(newUser.getLastname());
            user.setFirstname(newUser.getFirstname());
            user.setEmail(newUser.getEmail());
            user.setUsername(newUser.getUsername());
            String hashedPassword = bcrypt.encode(newUser.getPassword());
            System.out.println(user.getFirstname());
            user.setPassword(hashedPassword);
            userRepository.save(user);
            mailService.newAccountEmail(user);
    	}else {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password Must Match");
    	}
		return newUser;
    }
    
    public User logUser(User user) {
    	System.out.println("hello");
    	Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
    	System.out.println(byUsername.get().getLastname()+" "+byUsername.get().getLastname());
    	if(byUsername.isPresent()) {
    		User dbUser = byUsername.get();
    		if(bcrypt.matches(user.getPassword(), dbUser.getPassword())) {
    			user = getUserData(user.getUsername());
    			return user;
    		}else {
    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Incorrect Password");
    		}
    	}else {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found");
    	}
    }
    
    public User getUserData(String username) {
    	return userRepository.checkUsername(username);
    }
    
    public List<User> findAllUsers(){
    	return userRepository.findAll();
    }
    
    public void deleteUser(int id) {
    	userRepository.deleteById(id);
    }
    
    public User getUserById(int id) {
    	return userRepository.getUserById(id);
    }
    
    public void UpdateUser(User user) {
    	userRepository.save(user);
    }
}
