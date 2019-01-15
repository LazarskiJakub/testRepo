package com.io.insurance.ClientData.controllers;

import com.io.insurance.ClientData.models.DamageReport;
import com.io.insurance.ClientData.models.DamageReportReponse;
import com.io.insurance.ClientData.models.Insurance;
import com.io.insurance.ClientData.service.ClientService;
import com.io.insurance.ClientData.service.DamageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class DamageReportController {
    @Autowired
    private DamageReportService damageReportService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/client/{ID}/damages")
    public DamageReportReponse getAllByClientId(@PathVariable("ID") String clientID){
        List<DamageReport> reports = new LinkedList<>();
        if(clientService.clientExists(clientID)) {
            reports = damageReportService.findAllByClientId(clientID);
            return new DamageReportReponse(200, "OK", reports);
        } else {
            return new DamageReportReponse(300, "Client with given ID does not exist", reports);
        }
    }

    @RequestMapping(value = "/client/{ID}/damages/{type}", method = GET)
    public DamageReportReponse getAllByClientIdAndType(
            @PathVariable("ID")String clientId,
            @PathVariable("type") String insuranceType){
        List<DamageReport> reports = new LinkedList<>();
        if(clientService.clientExists(clientId)) {
            Insurance.InsuranceType type;
            try {
                type = Insurance.InsuranceType.valueOf(insuranceType.toUpperCase());
                reports = damageReportService.findAllByClientIdAndType(clientId, type);
                return new DamageReportReponse(200, "OK", reports);
            } catch (IllegalArgumentException e){
                return new DamageReportReponse(303, "Incorrect request parameters", null);
            }
        } else {
            return new DamageReportReponse(300, "Client with given ID does not exist", reports);
        }
    }


    @GetMapping("/damage/{ID}")
    public DamageReportReponse getById(@PathVariable("ID") String damageReportId){
        List<DamageReport> reports = new LinkedList<>();
        DamageReport report = damageReportService.findById(damageReportId);
        if(Objects.nonNull(report)) {
            reports.add(report);
            return new DamageReportReponse(200, "OK", reports);
        } else {
            return new DamageReportReponse(301, "Data with given ID does not exist\n", reports);
        }
    }

    @PostMapping("/client/{ID}/damage/{type}")
    public DamageReportReponse saveDamageReport(@PathVariable(value = "ID") String clientId,
                                                @PathVariable(value = "type") String insuranceType,
                                                @RequestBody Map<String,String> damageReport){
        if(clientService.clientExists(clientId)) {
            if(Objects.nonNull(damageReport)){
                Insurance.InsuranceType type;
                try {
                    type = Insurance.InsuranceType.valueOf(insuranceType.toUpperCase());
                    DamageReport report = new DamageReport(clientId, type, damageReport);
                    String id = damageReportService.save(report);
                    report.setId(id);
                    return new DamageReportReponse(201, "Data saved successfully", new LinkedList<DamageReport>(){{add(report);}});
                } catch (IllegalArgumentException e){
                    return new DamageReportReponse(303, "Incorrect request parameters", null);
                }
            }
            return new DamageReportReponse(303, "Incorrect request parameters", null);
        } else {
            return new DamageReportReponse(300, "Client with given ID does not exist", null);
        }
    }

    @PutMapping("/client/{clientID}/damage/{damageID}")
    public DamageReportReponse updateDamageReport(@PathVariable(value = "clientID") String clientId,
                                                  @PathVariable(value = "damageID") String damageReportId,
                                                  @RequestBody Map<String,String> damageReport){
        if(clientService.clientExists(clientId)) {
            DamageReport report = damageReportService.findById(damageReportId);
            if(Objects.nonNull(report)) {
                if (Objects.nonNull(damageReport)) {
                    report.setData(damageReport);
                    damageReportService.save(report);
                    return new DamageReportReponse(202, "Data updated successfully\n", new LinkedList<DamageReport>() {{
                        add(report);
                    }});
                }
                return new DamageReportReponse(303, "Incorrect request parameters", null);
            }
            return new DamageReportReponse(301, "Data with given ID does not exist", null);
        } else {
            return new DamageReportReponse(300, "Client with given ID does not exist", null);
        }
    }

    @DeleteMapping("/client/{clientID}/damage/{damageID}")
    public DamageReportReponse deleteDamageReport(@PathVariable(value = "clientID") String clientId,
                                                  @PathVariable(value = "damageID") String damageReportId){
        if(clientService.clientExists(clientId)) {
            DamageReport report = damageReportService.findById(damageReportId);
            if(Objects.nonNull(report)) {
                damageReportService.delete(damageReportId);
                return new DamageReportReponse(203, "Data removed successfully", null);
            }
            return new DamageReportReponse(301, "Data with given ID does not exist", null);
        } else {
            return new DamageReportReponse(300, "Client with given ID does not exist", null);
        }
    }
}
