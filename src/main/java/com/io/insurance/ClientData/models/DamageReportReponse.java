package com.io.insurance.ClientData.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DamageReportReponse {

    private int code;
    private String message;
    private List<DamageReport> payload;
}
