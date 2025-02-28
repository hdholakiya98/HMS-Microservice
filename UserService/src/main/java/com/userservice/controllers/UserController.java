package com.userservice.controllers;

import com.userservice.Services.UserService;
import com.userservice.entities.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController{

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User createdUser = userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    int retryCount= 1;
    @GetMapping("/{userId}")
    //@CircuitBreaker(name ="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    //@Retry(name ="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name ="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        logger.info("Retry:{}",retryCount);
        retryCount++;
        User getUser = userService.getUser(userId);
        return new ResponseEntity<>(getUser, HttpStatus.OK);
    }

    public ResponseEntity<String> ratingHotelFallback(String userId, Exception ex) {
        return ResponseEntity.ok("Service is currently unavailable. Please try again later.");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> getAll = userService.getAllUsers();
        return new ResponseEntity<>(getAll, HttpStatus.OK);
        //return ResponseEntity.ok(userService.getAllUsers());
    }
}