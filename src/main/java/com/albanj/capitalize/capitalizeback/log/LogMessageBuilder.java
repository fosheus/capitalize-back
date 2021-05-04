package com.albanj.capitalize.capitalizeback.log;

import com.albanj.capitalize.capitalizeback.entity.RefProfile;

import org.springframework.security.core.Authentication;

public class LogMessageBuilder {

	public static String buildHeader(Authentication authentication) {
		RefProfile role = null;
		if (authentication.getAuthorities().size() > 0) {
			role = (RefProfile) authentication.getAuthorities().toArray()[0];
		}
		return "USER=" + authentication.getName() + " - ROLE=" + role.getLabel() + "";
	}
}
