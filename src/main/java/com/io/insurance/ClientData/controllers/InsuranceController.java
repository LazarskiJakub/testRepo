package com.io.insurance.ClientData.controllers;

import com.io.insurance.ClientData.models.Insurance;
import com.io.insurance.ClientData.models.InsuranceResponse;
import com.io.insurance.ClientData.service.ClientService;
import com.io.insurance.ClientData.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class InsuranceController {
    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private ClientService clientService;



    @ExceptionHandler({Exception.class})
    public InsuranceResponse handleError(HttpServletRequest request, Exception e)   {
        e.printStackTrace();
        return new InsuranceResponse(500, "Internal error", null);
    }

    @RequestMapping(value = "/client/{ID}/insurances", method = GET)
    public InsuranceResponse getAllByClientId(
            @PathVariable("ID")String clientId){
        List<Insurance> insurancesList = new LinkedList<>();
        if(clientService.clientExists(clientId)) {
            insurancesList = insuranceService.findAllByClientId(clientId);
            return new InsuranceResponse(200, "OK", insurancesList);
        } else {
            return new InsuranceResponse(300, "Client with given ID does not exist", insurancesList);
        }
    }

    @RequestMapping(value = "/client/{ID}/insurances/{type}", method = GET)
    public InsuranceResponse getAllByClientIdAndType(
            @PathVariable("ID")String clientId,
            @PathVariable("type") String insuranceType){
        List<Insurance> insurancesList = new LinkedList<>();
        if(clientService.clientExists(clientId)) {
            Insurance.InsuranceType type;
            try {
                type = Insurance.InsuranceType.valueOf(insuranceType.toUpperCase());
                insurancesList = insuranceService.findAllByClientIdAndType(clientId, type);

                return new InsuranceResponse(200, "OK", insurancesList);
            } catch (IllegalArgumentException e){
                return new InsuranceResponse(303, "Incorrect request parameters", null);
            }
        } else {
            return new InsuranceResponse(300, "Client with given ID does not exist", insurancesList);
        }
    }

    @RequestMapping(value = "/insurance/{ID}", method = GET)
    public InsuranceResponse getById(
            @PathVariable("ID")String insuranceId){
        List<Insurance> insurancesList = new LinkedList<>();
        Insurance insurance = insuranceService.findById(insuranceId);
        if(Objects.nonNull(insurance)) {
            insurancesList.add(insurance);
            return new InsuranceResponse(200, "OK", insurancesList);
        } else {
            return new InsuranceResponse(301, "Data with given ID does not exist\n", insurancesList);
        }
    }

    @RequestMapping(value = "/client/{ID}/insurance/{type}", method = POST)
    public InsuranceResponse saveInsurance(
            @PathVariable("ID")String clientId,
            @PathVariable("type") String insuranceType,
            @RequestBody Map<String,String> insurance){
        if(clientService.clientExists(clientId)) {
            if(Objects.nonNull(insurance)){
                Insurance.InsuranceType type;
                try {
                    type = Insurance.InsuranceType.valueOf(insuranceType.toUpperCase());
                    Insurance newInsurance = new Insurance(clientId, type, insurance);
                    String id = insuranceService.save(newInsurance);
                    newInsurance.setId(id);
                    return new InsuranceResponse(201, "Data saved successfully",
                            new LinkedList<Insurance>(){{add(newInsurance);}});
                } catch (IllegalArgumentException e){
                    return new InsuranceResponse(303, "Incorrect request parameters", null);
                }
            }
            return new InsuranceResponse(303, "Incorrect request parameters", null);
        } else {
            return new InsuranceResponse(300, "Client with given ID does not exist", null);
        }
    }

    @RequestMapping(value = "/client/{clientID}/insurance/{insuranceID}", method = PUT)
    public InsuranceResponse updateInsurance(
            @PathVariable("clientID")String clientId,
            @PathVariable("insuranceID")String insuranceId,
            @RequestBody Map<String,String> insuranceData){
        if(clientService.clientExists(clientId)) {
            Insurance insurance = insuranceService.findById(insuranceId);
            if(Objects.nonNull(insurance)) {
                if (Objects.nonNull(insuranceData)) {
                    insurance.setData(insuranceData);
                    String id = insuranceService.save(insurance);
                    insurance.setId(id);
                    return new InsuranceResponse(201, "Data saved successfully",
                            new LinkedList<Insurance>() {{
                                add(insurance);
                            }});
                }
                return new InsuranceResponse(303, "Incorrect request parameters", null);
            } else {
                return new InsuranceResponse(301, "Data with given ID does not exist", null);
            }
        } else {
            return new InsuranceResponse(300, "Client with given ID does not exist", null);
        }
    }
    @RequestMapping(value = "/client/{clientID}/insurance/{insuranceID}", method = DELETE)
    public InsuranceResponse deleteInsurance(
            @PathVariable("clientID")String clientId,
            @PathVariable("insuranceID")String insuranceId){
        if(clientService.clientExists(clientId)) {
            Insurance insurance = insuranceService.findById(insuranceId);
            if(Objects.nonNull(insurance)) {
                insuranceService.delete(insuranceId);
                return new InsuranceResponse(203, "Data removed successfully", null);
            }
            return new InsuranceResponse(301, "Data with given ID does not exist", null);
        } else {
            return new InsuranceResponse(300, "Client with given ID does not exist", null);
        }
    }
}