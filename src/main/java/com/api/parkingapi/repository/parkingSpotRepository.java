package com.api.parkingapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.parkingapi.models.parkingSpotModel;

@Repository
public interface parkingSpotRepository extends JpaRepository<parkingSpotModel, UUID> {

    boolean existsByCarNumber(String carNumber);

    boolean existsByResponsibleName(String responsibleName);

    boolean existsByParkingNumber(String parkingNumber);

}
