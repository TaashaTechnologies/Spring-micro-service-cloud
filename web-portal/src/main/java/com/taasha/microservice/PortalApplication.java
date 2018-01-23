package com.taasha.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
@EnableTurbine
public class PortalApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.logout().and().authorizeRequests().antMatchers("/**/*.html", "/", "/login","/hystrix/**","/turbine.stream").permitAll().anyRequest()
				.authenticated();
		// @formatter:on
	}
}
