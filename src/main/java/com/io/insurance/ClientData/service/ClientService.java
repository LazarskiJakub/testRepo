package com.io.insurance.ClientData.service;

import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private static final String GLOBAL_ID = "client-1234-io";

    public boolean clientExists(String clientId) {
        return clientId.equals(GLOBAL_ID);
    }
}
