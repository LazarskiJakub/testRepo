package com.io.insurance.ClientData.service;

import com.io.insurance.ClientData.models.Insurance;
import com.io.insurance.ClientData.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceService {
    @Autowired
    private InsuranceRepository repository;

    public Insurance findById(String insuranceId) {
        if (repository.findById(insuranceId).isPresent())
            return repository.findById(insuranceId).get();
        return null;
    }

    public String save(Insurance insurance) {
        Insurance saved = repository.save(insurance);
        return saved.getId();
    }

    public String update(Insurance insurance) {
        Insurance updated = repository.save(insurance);
        return updated.getId();
    }

    public boolean delete(Insurance insurance) {
        repository.delete(insurance);
        return true;
    }

    public boolean delete(String insuranceId) {
        repository.deleteById(insuranceId);
        return true;
    }

    public List<Insurance> findAllByClientId(String clientId) {
        List<Insurance> insurances = repository.findAllByClientId(clientId);
        return insurances;
    }

    public List<Insurance> findAllByClientIdAndType(String clientId, Insurance.InsuranceType insuranceType) {
        List<Insurance> insurances = repository.findAllByClientIdAndType(clientId, insuranceType.name());
        return insurances;
    }
}
