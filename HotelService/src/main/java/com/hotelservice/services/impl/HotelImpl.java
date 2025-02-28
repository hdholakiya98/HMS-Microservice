package com.hotelservice.services.impl;

import com.hotelservice.entities.Hotel;
import com.hotelservice.exceptions.ResourceNotFoundException;
import com.hotelservice.repositories.HotelRepository;
import com.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotel createHotel(Hotel hotel) {

        String randomUserId = UUID.randomUUID().toString();
        hotel.setId(randomUserId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with the given ID not found"));
    }
}
