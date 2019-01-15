package com.io.insurance.ClientData.repository;

import com.io.insurance.ClientData.models.Insurance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InsuranceRepository extends MongoRepository<Insurance, String> {
    List<Insurance> findAllByClientId(String clientId);
    List<Insurance> findAllByClientIdAndType(String clientId, String insuranceType);
}
