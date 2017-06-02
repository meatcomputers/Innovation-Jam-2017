package com.travelers.hackathon.data.DriverDashboardUI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class DriverDashboardUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverDashboardUiApplication.class, args);
	}
}
