package com.islamahmad.tidy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
public class TidyApplication implements CommandLineRunner{
	final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired ResourceLoader resourceLoader  ; 
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TidyApplication.class);
        app.run(args);
	}

	@Override 
	public void run(String... args) throws Exception {Checker.check(resourceLoader);}
}
