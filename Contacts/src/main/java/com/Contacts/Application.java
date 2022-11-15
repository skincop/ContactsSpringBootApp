package com.Contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import static java.util.Collections.singletonList;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	public Application(FreeMarkerConfigurer freeMarkerConfigurer) {
		freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(singletonList("/META-INF/security.tld"));
	}
}