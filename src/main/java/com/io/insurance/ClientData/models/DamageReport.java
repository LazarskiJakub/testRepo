package com.io.insurance.ClientData.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DamageReport {

    @Id
    private String id;

    @Field
    private String clientId;

    @Field
    private Insurance.InsuranceType type;

    @Field
    private Map<String, String> data;

    public DamageReport(String clientId, Insurance.InsuranceType type, Map<String, String> data) {
        this.clientId = clientId;
        this.type = type;
        this.data = data;
    }
}
