package com.io.insurance.ClientData.repository;

import com.io.insurance.ClientData.models.DamageReport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DamageReportRepository extends MongoRepository<DamageReport, String> {
    List<DamageReport> findAllByClientId(String clientId);
    List<DamageReport> findAllByClientIdAndType(String clientId, String insuranceType);
}
