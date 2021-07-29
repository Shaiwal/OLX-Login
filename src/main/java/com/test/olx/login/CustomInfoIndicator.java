package com.test.olx.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
/*
 * First approach for Custom info actuator use either class or InfoWebEndpointExtension
 */
@Component
public class CustomInfoIndicator implements InfoContributor{

	@Override
	public void contribute(Builder builder) {
		// TODO Auto-generated method stub
		Random r = new Random();
		int totalUsers = r.nextInt();
		int activeLoginCount = r.nextInt();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("totalUsers", totalUsers);
		m.put("activeLoginCount", activeLoginCount);
		builder.withDetails(m);
		
	}

}
