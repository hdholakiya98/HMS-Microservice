package com.userservice.Services.impl;

import com.userservice.Services.UserService;
import com.userservice.entities.Hotel;
import com.userservice.entities.Rating;
import com.userservice.entities.User;
import com.userservice.exceptions.ResourceNotFoundException;
import com.userservice.external.services.HotelService;
import com.userservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {

        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Fetch user ratings from the Rating Service
        Rating[] ratingsArray = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("User Ratings: {}", ratingsArray);

        // Convert array to list
        List<Rating> ratingsList = Arrays.asList(ratingsArray);

        // Fetch hotel details for each rating
        for (Rating rating : ratingsList) {
            //Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
        }
        user.setRatings(ratingsList);
        return user;
    }
}