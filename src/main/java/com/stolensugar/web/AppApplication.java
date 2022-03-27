package com.stolensugar.web;

import com.stolensugar.web.dagger.DaggerApplicationComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stolensugar.web.dagger.ApplicationComponent;

@SpringBootApplication
public class AppApplication {

	public static final ApplicationComponent component = DaggerApplicationComponent.create();

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
}
