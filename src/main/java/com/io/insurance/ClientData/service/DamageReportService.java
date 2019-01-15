package com.io.insurance.ClientData.service;

import com.io.insurance.ClientData.models.DamageReport;
import com.io.insurance.ClientData.models.Insurance;
import com.io.insurance.ClientData.repository.DamageReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageReportService {
    @Autowired
    private DamageReportRepository repository;

    public DamageReport findById(String damageReportId) {
        if (repository.findById(damageReportId).isPresent())
            return repository.findById(damageReportId).get();
        return null;
    }

    public String save(DamageReport damageReport) {
        DamageReport saved = repository.save(damageReport);
        return saved.getId();
    }

    public String update(DamageReport damageReport) {
        DamageReport updated = repository.save(damageReport);
        return updated.getId();
    }

    public boolean delete(DamageReport damageReport) {
        repository.delete(damageReport);
        return true;
    }

    public boolean delete(String damageReportId) {
        repository.deleteById(damageReportId);
        return true;
    }

    public List<DamageReport> findAllByClientId(String clientId) {
        List<DamageReport> damageReports = repository.findAllByClientId(clientId);
        return damageReports;
    }

    public List<DamageReport> findAllByClientIdAndType(String clientId, Insurance.InsuranceType insuranceType) {
        List<DamageReport> damageReports = repository.findAllByClientIdAndType(clientId, insuranceType.name());
        return damageReports;
    }
}
