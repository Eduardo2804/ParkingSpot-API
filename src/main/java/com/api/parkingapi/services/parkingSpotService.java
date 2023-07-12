package com.api.parkingapi.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.parkingapi.models.parkingSpotModel;
import com.api.parkingapi.repository.parkingSpotRepository;

import jakarta.transaction.Transactional;

@Service
public class parkingSpotService {

    @Autowired
    parkingSpotRepository parkingSpotRepository;

    @Transactional
    public Object save(parkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public boolean existsByCarNumber(String carNumber) {

        return parkingSpotRepository.existsByCarNumber(carNumber);
    }

    public boolean existsByResponsibleName(String responsibleName) {

        return parkingSpotRepository.existsByResponsibleName(responsibleName);
    }

    public boolean existsByParkingNumber(String parkingNumber) {

        return parkingSpotRepository.existsByParkingNumber(parkingNumber);
    }

    public java.util.List<parkingSpotModel> findAll() {
        return parkingSpotRepository.findAll();
    }

    public Optional<parkingSpotModel> findById(UUID id) {
        return parkingSpotRepository.findById(id);
    }

    @Transactional
    public void delete(parkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }

}
