package com.test.olx.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;

/*
 * second approach for Custom info actuator use either CustomInfoIndicator or this class
 */
@Component
@EndpointWebExtension(endpoint = InfoEndpoint.class)
public class InfoWebEndpointExtension {

    private InfoEndpoint delegate;

    // standard constructor

    @ReadOperation
    public WebEndpointResponse<Map> info() {
       
        Random r = new Random();
		int totalUsers = r.nextInt();
		int activeLoginCount = r.nextInt();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("totalUsers", totalUsers);
		m.put("activeLoginCount", activeLoginCount);
        return new WebEndpointResponse<>(m);
    }

    private Integer getStatus(Map<String, Object> info) {
        // return 5xx if this is a snapshot
        return 200;
    }
}