package com.api.parkingapi.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingapi.dtos.parkingSpotDtos;
import com.api.parkingapi.models.parkingSpotModel;
import com.api.parkingapi.services.parkingSpotService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parkingSpot")
public class parkingSpotController {

    @Autowired
    parkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid parkingSpotDtos parkingSpotDtos) {

        // validations

        if (parkingSpotService.existsByCarNumber(parkingSpotDtos.getCarNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: This car is registered");
        }

        if (parkingSpotService.existsByResponsibleName(parkingSpotDtos.getResponsibleName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: User already registered");
        }

        if (parkingSpotService.existsByParkingNumber(parkingSpotDtos.getParkingNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT: Parking number is already in use");
        }

        var parkingSpotModel = new com.api.parkingapi.models.parkingSpotModel();

        BeanUtils.copyProperties(parkingSpotDtos, parkingSpotModel);
        parkingSpotModel.setRegistrationdate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    // method get all the parking spots

    @GetMapping
    public ResponseEntity<List<parkingSpotModel>> getAllParkingSpots() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());

    }

    // method get by id

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkingSpotById(@PathVariable(value = "id") UUID id) {

        Optional<parkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Parking number not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());

    }

    // method delete

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {

        Optional<parkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Parking number not found");
        }

        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking spot has been deleted sucessfully");

    }

    // method uptdate

    @PutMapping("{id}")
    public ResponseEntity<Object> uptadeParkingSpot(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid parkingSpotDtos parkingDTO) {

        Optional<parkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Parking number not found");
        }

        var parkingSpotModel = parkingSpotModelOptional.get();
        parkingSpotModel.setParkingNumber(parkingDTO.getParkingNumber());
        parkingSpotModel.setCarNumber(parkingDTO.getCarNumber());
        parkingSpotModel.setBrandCar(parkingDTO.getBrandCar());
        parkingSpotModel.setColorCar(parkingDTO.getModelCar());
        parkingSpotModel.setModelCar(parkingDTO.getModelCar());
        parkingSpotModel.setBlock(parkingDTO.getBlock());
        parkingSpotModel.setResponsibleName(parkingDTO.getResponsibleName());

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));

    }

}
