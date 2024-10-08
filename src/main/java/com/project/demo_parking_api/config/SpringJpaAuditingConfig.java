package com.project.demo_parking_api.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class SpringJpaAuditingConfig implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication =	SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			return Optional.of(authentication.getName());
		}
		return null;
	}

	
}
